/**
 * 
	  'use strict';

	  /** Hides a DOM element and optionally focuses on focusEl. */
function hideElement(el, focusEl) {
	el.style.display = 'none';
	if (focusEl) focusEl.focus();
}

/** Shows a DOM element that has been hidden and optionally focuses on focusEl. */
function showElement(el, focusEl) {
	el.style.display = 'block';
	if (focusEl) focusEl.focus();
}

/** Determines if a DOM element contains content that cannot be scrolled into view. */
function hasHiddenContent(el) {
	const noscroll = window.getComputedStyle(el).overflowY.includes('hidden');
	return noscroll && el.scrollHeight > el.clientHeight;
}

/** Format a Place Type string by capitalizing and replacing underscores with spaces. */
function formatPlaceType(str) {
	const capitalized = str.charAt(0).toUpperCase() + str.slice(1);
	return capitalized.replace(/_/g, ' ');
}

/** Initializes an array of zeros with the given size. */
function initArray(arraySize) {
	const array = [];
	while (array.length < arraySize) {
		array.push(0);
	}
	return array;
}

/** Assigns star icons to an object given its rating (out of 5). */
function addStarIcons(obj) {
	if (!obj.rating) return;
	const starsOutOfTen = Math.round(2 * obj.rating);
	const fullStars = Math.floor(starsOutOfTen / 2);
	const halfStars = fullStars !== starsOutOfTen / 2 ? 1 : 0;
	const emptyStars = 5 - fullStars - halfStars;

	// Express stars as arrays to make iterating in Handlebars easy.
	obj.fullStarIcons = initArray(fullStars);
	obj.halfStarIcons = initArray(halfStars);
	obj.emptyStarIcons = initArray(emptyStars);
}

/**
 * Constructs an array of opening hours by day from a PlaceOpeningHours object,
 * where adjacent days of week with the same hours are collapsed into one element.
 */
function parseDaysHours(openingHours) {
	const daysHours = openingHours.weekday_text.map((e) => e.split(/\:\s+/))
		.map((e) => ({ 'days': e[0].substr(0, 3), 'hours': e[1] }));

	for (let i = 1; i < daysHours.length; i++) {
		if (daysHours[i - 1].hours === daysHours[i].hours) {
			if (daysHours[i - 1].days.indexOf('-') !== -1) {
				daysHours[i - 1].days =
					daysHours[i - 1].days.replace(/\w+$/, daysHours[i].days);
			} else {
				daysHours[i - 1].days += ' - ' + daysHours[i].days;
			}
			daysHours.splice(i--, 1);
		}
	}
	return daysHours;
}

/** Number of POIs to show on widget load. */
const ND_NUM_PLACES_INITIAL = 32;

/** Number of additional POIs to show when 'Show More' button is clicked. */
const ND_NUM_PLACES_SHOW_MORE = 5;

/** Maximum number of place photos to show on the details panel. */
const ND_NUM_PLACE_PHOTOS_MAX = 6;

/** Minimum zoom level at which the default map POI pins will be shown. */
const ND_DEFAULT_POI_MIN_ZOOM = 18;

/** Mapping of Place Types to Material Icons used to render custom map markers. */
const ND_MARKER_ICONS_BY_TYPE = {
	// Full list of icons can be found at https://fonts.google.com/icons
	'_default': 'circle',
	'restaurant': 'restaurant',
	'cafe': 'local_cafe',
	'night_club': 'nightlife',
	'bar': 'local_bar',
	'park': 'park',
	'movie_theater': 'theaters',
	'museum': 'museum',
	'book_store': 'local_mall',
	'department_store': 'local_mall',
	'primary_school': 'school',
	'secondary_school': 'school',
	'tourist_attraction': 'local_see',
};

/**
 * Defines an instance of the Neighborhood Discovery widget, to be
 * instantiated when the Maps library is loaded.
 */
function NeighborhoodDiscovery(configuration) {
	const widget = this;
	const widgetEl = document.querySelector('.neighborhood-discovery');

	widget.center = configuration.mapOptions.center;
	widget.places = configuration.pois || [];

	// Initialize core functionalities -------------------------------------

	initializeMap();
	initializePlaceDetails();
	initializeSidePanel();

	// Initialize additional capabilities ----------------------------------
	initializeSearchInput();

	// Initializer function definitions ------------------------------------

	/** Initializes the interactive map and adds place markers. */
	function initializeMap() {
		const mapOptions = configuration.mapOptions;
		widget.mapBounds = new google.maps.Circle(
			{ center: widget.center, radius: configuration.mapRadius }).getBounds();
		mapOptions.restriction = { latLngBounds: widget.mapBounds };
		mapOptions.mapTypeControlOptions = { position: google.maps.ControlPosition.TOP_RIGHT };
		widget.map = new google.maps.Map(widgetEl.querySelector('.map'), mapOptions);
		widget.map.fitBounds(widget.mapBounds, /* padding= */ 0);
		widget.map.addListener('click', (e) => {
			// Check if user clicks on a POI pin from the base map.
			if (e.placeId) {
				e.stop();
				widget.selectPlaceById(e.placeId);
			}
		});
		widget.map.addListener('zoom_changed', () => {
			// Customize map styling to show/hide default POI pins or text based on zoom level.
			const hideDefaultPoiPins = widget.map.getZoom() < ND_DEFAULT_POI_MIN_ZOOM;
			widget.map.setOptions({
				styles: [{
					featureType: 'poi',
					elementType: hideDefaultPoiPins ? 'labels' : 'labels.text',
					stylers: [{ visibility: 'off' }],
				}],
			});
		});

		const markerPath = widgetEl.querySelector('.marker-pin path').getAttribute('d');
		const drawMarker = function(title, position, fillColor, strokeColor, labelText) {
			return new google.maps.Marker({
				title: title,
				position: position,
				map: widget.map,
				icon: {
					path: markerPath,
					fillColor: fillColor,
					fillOpacity: 1,
					strokeColor: strokeColor,
					anchor: new google.maps.Point(13, 35),
					labelOrigin: new google.maps.Point(13, 13),
				},
				label: {
					text: labelText,
					color: 'white',
					fontSize: '16px',
					fontFamily: 'Material Icons',
				},
			});
		};

		// Add marker for the specified Place object.
		widget.addPlaceMarker1 = function(place) {
			// 기존 마커 제거 함수 정의
			place.removeMarker = function() {
				if (place.marker) {
					// 이벤트 리스너 제거
					google.maps.event.clearInstanceListeners(place.marker);

					console.log("마커 제거 시도:", place.marker);
					place.marker.setMap(null); // 마커를 지도에서 제거
					console.log("마커 제거됨:", place.marker);
					delete place.marker; // 메모리 누수 방지를 위해 마커 객체 제거
				} else {
					console.log("마커가 설정되지 않음:", place);
				}
			};

			// 기존 마커가 있는지 확인하고 제거
			if (place.marker) {
				console.log('기존 마커 제거 시도:', place.marker);
				place.removeMarker();
			}

			// 새로운 마커 생성 및 설정
			place.marker = drawMarker(place.name, place.coords, '#EA4335', '#C5221F', place.icon);
			place.marker.addListener('click', () => void widget.selectPlaceById(place.placeId));
		};




		// Marker used to highlight a place from Autocomplete search.
		widget.selectedPlaceMarker = new google.maps.Marker({ title: 'Point of Interest' });
	}

	/** Initializes Place Details service for the widget. */
	function initializePlaceDetails() {
		const detailsService = new google.maps.places.PlacesService(widget.map);
		const placeIdsToDetails = new Map();  // Create object to hold Place results.

		for (let place of widget.places) {
			placeIdsToDetails.set(place.placeId, place);
			place.fetchedFields = new Set(['place_id']);
		}

		widget.fetchPlaceDetails = function(placeId, fields, callback) {
			if (!placeId || !fields) return;

			// Check for field existence in Place object.
			let place = placeIdsToDetails.get(placeId);
			if (!place) {
				place = { placeId: placeId, fetchedFields: new Set(['place_id']) };
				placeIdsToDetails.set(placeId, place);
			}
			const missingFields = fields.filter((field) => !place.fetchedFields.has(field));
			if (missingFields.length === 0) {
				callback(place);
				return;
			}

			const request = { placeId: placeId, fields: missingFields };
			let retryCount = 0;
			const processResult = function(result, status) {
				if (status !== google.maps.places.PlacesServiceStatus.OK) {
					// If query limit has been reached, wait before making another call;
					// Increase wait time of each successive retry with exponential backoff
					// and terminate after five failed attempts.
					if (status === google.maps.places.PlacesServiceStatus.OVER_QUERY_LIMIT &&
						retryCount < 5) {
						const delay = (Math.pow(2, retryCount) + Math.random()) * 500;
						setTimeout(() => void detailsService.getDetails(request, processResult), delay);
						retryCount++;
					}
					return;
				}

				// Basic details.
				if (result.name) place.name = result.name;
				if (result.geometry) place.coords = result.geometry.location;
				if (result.formatted_address) place.address = result.formatted_address;
				if (result.photos) {
					place.photos = result.photos.map((photo) => ({
						urlSmall: photo.getUrl({ maxWidth: 200, maxHeight: 200 }),
						urlLarge: photo.getUrl({ maxWidth: 1200, maxHeight: 1200 }),
						attrs: photo.html_attributions,
					})).slice(0, ND_NUM_PLACE_PHOTOS_MAX);
				}
				if (result.types) {
					place.type = formatPlaceType(result.types[0]);
					place.icon = ND_MARKER_ICONS_BY_TYPE['_default'];
					for (let type of result.types) {
						if (type in ND_MARKER_ICONS_BY_TYPE) {
							place.type = formatPlaceType(type);
							place.icon = ND_MARKER_ICONS_BY_TYPE[type];
							break;
						}
					}
				}
				if (result.url) place.url = result.url;

				// Contact details.
				if (result.website) {
					place.website = result.website;
					const url = new URL(place.website);
					place.websiteDomain = url.hostname;
				}
				if (result.formatted_phone_number) place.phoneNumber = result.formatted_phone_number;
				if (result.opening_hours) place.openingHours = parseDaysHours(result.opening_hours);

				// Review details.
				if (result.rating) {
					place.rating = result.rating;
					addStarIcons(place);
				}
				if (result.user_ratings_total) place.numReviews = result.user_ratings_total;
				if (result.price_level) {
					place.priceLevel = result.price_level;
					place.dollarSigns = initArray(result.price_level);
				}
				if (result.reviews) {
					place.reviews = result.reviews;
					for (let review of place.reviews) {
						addStarIcons(review);
					}
				}

				for (let field of missingFields) {
					place.fetchedFields.add(field);
				}
				callback(place);
			};

			// Use result from Place Autocomplete if available.
			if (widget.placeIdsToAutocompleteResults) {
				const autoCompleteResult = widget.placeIdsToAutocompleteResults.get(placeId);
				if (autoCompleteResult) {
					processResult(autoCompleteResult, google.maps.places.PlacesServiceStatus.OK);
					return;
				}
			}
			detailsService.getDetails(request, processResult);
		};
	}

	/** Initializes the side panel that holds curated POI results. */
	function initializeSidePanel() {
		const placesPanelEl = widgetEl.querySelector('.places-panel');
		const detailsPanelEl = widgetEl.querySelector('.details-panel');
		const placeResultsEl = widgetEl.querySelector('.place-results-list');
		const showMoreButtonEl = widgetEl.querySelector('.show-more-button');
		const photoModalEl = widgetEl.querySelector('.photo-modal');
		const detailsTemplate = Handlebars.compile(
			document.getElementById('nd-place-details-tmpl').innerHTML);
		const resultsTemplate = Handlebars.compile(
			document.getElementById('nd-place-results-tmpl').innerHTML);

		// Show specified POI photo in a modal.
		const showPhotoModal = function(photo, placeName) {
			const prevFocusEl = document.activeElement;
			const imgEl = photoModalEl.querySelector('img');
			imgEl.src = photo.urlLarge;
			const backButtonEl = photoModalEl.querySelector('.back-button');
			backButtonEl.addEventListener('click', () => {
				hideElement(photoModalEl, prevFocusEl);
				imgEl.src = '';
			});
			photoModalEl.querySelector('.photo-place').innerHTML = placeName;
			photoModalEl.querySelector('.photo-attrs span').innerHTML = photo.attrs;
			const attributionEl = photoModalEl.querySelector('.photo-attrs a');
			if (attributionEl) attributionEl.setAttribute('target', '_blank');
			photoModalEl.addEventListener('click', (e) => {
				if (e.target === photoModalEl) {
					hideElement(photoModalEl, prevFocusEl);
					imgEl.src = '';
				}
			});
			showElement(photoModalEl, backButtonEl);
		};

		// Select a place by id and show details.
		let selectedPlaceId;
		widget.selectPlaceById = function(placeId, panToMarker) {
			if (selectedPlaceId === placeId) return;
			selectedPlaceId = placeId;
			const prevFocusEl = document.activeElement;

			const showDetailsPanel = function(place) {
				detailsPanelEl.innerHTML = detailsTemplate(place);
				const backButtonEl = detailsPanelEl.querySelector('.back-button');
				backButtonEl.addEventListener('click', () => {
					hideElement(detailsPanelEl, prevFocusEl);
					selectedPlaceId = undefined;
					widget.selectedPlaceMarker.setMap(null); ㅋ
				});
				detailsPanelEl.querySelectorAll('.photo').forEach((photoEl, i) => {
					photoEl.addEventListener('click', () => {
						showPhotoModal(place.photos[i], place.name);
					});
				});
				showElement(detailsPanelEl, backButtonEl);
				detailsPanelEl.scrollTop = 0;
			};

			const processResult = function(place) {
				if (place.marker) {
					widget.selectedPlaceMarker.setMap(null);
				} else {
					widget.selectedPlaceMarker.setPosition(place.coords);
					widget.selectedPlaceMarker.setMap(widget.map);
				}
				if (panToMarker) {
					widget.map.panTo(place.coords);
				}
				showDetailsPanel(place);
			};

			widget.fetchPlaceDetails(placeId, [
				'name', 'types', 'geometry.location', 'formatted_address', 'photo', 'url',
				'website', 'formatted_phone_number', 'opening_hours',
				'rating', 'user_ratings_total', 'price_level', 'review',
			], processResult);
		};

		// Render the specified place objects and append them to the POI list.
		//고요한
		const renderPlaceResults = function(places, startIndex) {
			placeResultsEl.insertAdjacentHTML('beforeend', resultsTemplate({ places: places }));
			let placeCounter = 0; // 추가된 장소의 개수를 저장할 변수

			let savedPlaces = [];
			let addedPlaces = [];

			let requestDataObject = JSON.parse(localStorage.getItem("requestData"));
			let names = [];
			if (requestDataObject && requestDataObject.destinationNames) {
				names = requestDataObject.destinationNames.map(item => item);
			}

			const newPanel = document.querySelector('.new-panel');
			const updatePlaceCounter = () => {
				newPanel.querySelector('.place-counter').textContent = `(${placeCounter})`;
			};

			if (names && names.length > 0) {
				names.forEach(name => {
					const place = places.find(place => place.name === name);
					if (!place || addedPlaces.includes(name)) return; // 중복 방지

					console.log('Removing marker for place:', place);
					console.log('Index of place:', places.indexOf(place));



					const placeElement = document.createElement('div');
					placeElement.classList.add('place-element');
					placeElement.style.display = 'flex';
					placeElement.style.alignItems = 'center';
					placeElement.textContent = name;

					// 마커 표시

					const removeimg = document.createElement('img');
					removeimg.src = '/images/cart-x-fill.svg'; // 이미지 경로 설정
					removeimg.alt = 'Del'; // 대체 텍스트 설정
					removeimg.style.width = '15px'; // 이미지 크기 조절 (필요에 따라 설정)
					removeimg.style.height = '15px'; // 이미지 크기 조절 (필요에 따라 설정)
					removeimg.classList.add('remove-btn');
					removeimg.style.marginLeft = 'auto';
					removeimg.addEventListener('click', () => {
						placeElement.remove();
						const index = addedPlaces.indexOf(name);
						if (index !== -1) {
							addedPlaces.splice(index, 1);
						}
						const savedIndex = savedPlaces.findIndex(item => item.placeId === place.placeId);
						if (savedIndex !== -1) {
							savedPlaces.splice(savedIndex, 1);
						}
						// 카운트 갱신
						placeCounter--;
						updatePlaceCounter();

						// 해당 장소의 마커도 제거

						place.removeMarker(places.indexOf(place));

						console.log(places.indexOf(place));
					});


					const img = document.createElement('img');
					img.src = '/images/caret-down-fill.svg'; // 이미지 경로 설정
					img.alt = 'Down'; // 대체 텍스트 설정
					img.style.width = '15px'; // 이미지 크기 조절 (필요에 따라 설정)
					img.style.height = '15px'; // 이미지 크기 조절 (필요에 따라 설정)

					img.classList.add('down-btn');
					img.addEventListener('click', () => {
						const nextElement = placeElement.nextElementSibling;
						if (nextElement && nextElement.parentElement === newPanel) {
							newPanel.insertBefore(nextElement, placeElement);
						}
						const index = addedPlaces.indexOf(name);
						if (index < addedPlaces.length - 1) {
							// 배열 순서 변경
							[savedPlaces[index], savedPlaces[index + 1]] = [savedPlaces[index + 1], savedPlaces[index]];
							[addedPlaces[index], addedPlaces[index + 1]] = [addedPlaces[index + 1], addedPlaces[index]];
						}
					});

					const img2 = document.createElement('img');
					img2.src = '/images/caret-up-fill.svg'; // 이미지 경로 설정
					img2.alt = 'Up'; // 대체 텍스트 설정
					img2.style.width = '15px'; // 이미지 크기 조절 (필요에 따라 설정)
					img2.style.height = '15px'; // 이미지 크기 조절 (필요에 따라 설정)

					img2.classList.add('up-btn');
					img2.addEventListener('click', () => {
						const prevElement = placeElement.previousElementSibling;
						if (prevElement && prevElement.tagName === 'DIV' && prevElement.parentElement === newPanel) {
							newPanel.insertBefore(placeElement, prevElement);
						}
						const index = addedPlaces.indexOf(name);
						if (index > 0) {
							// 배열 순서 변경
							[savedPlaces[index], savedPlaces[index - 1]] = [savedPlaces[index - 1], savedPlaces[index]];
							[addedPlaces[index], addedPlaces[index - 1]] = [addedPlaces[index - 1], addedPlaces[index]];
						}
					});

					placeElement.appendChild(removeimg);
					placeElement.appendChild(img);
					placeElement.appendChild(img2);

					newPanel.appendChild(placeElement);

					// 카운트 갱신
					placeCounter++;
					updatePlaceCounter();



					const placeData = {
						placeId: place.placeId,
						travelMode: 'PUBLIC_TRANSIT'
					};
					savedPlaces.push(placeData);
					addedPlaces.push(name);
					widget.addPlaceMarker1(place);
				});

			}

			placeResultsEl.querySelectorAll('.place-result').forEach((resultEl, i) => {
				const place = places[i - startIndex];
				if (!place) return;

				resultEl.querySelector('.name').addEventListener('click', (e) => {
					widget.selectPlaceById(place.placeId, /* panToMarker= */ true);
					e.stopPropagation();
				});
				resultEl.querySelector('.photo').addEventListener('click', (e) => {
					showPhotoModal(place.photos[0], place.name);
					e.stopPropagation();
				});
				resultEl.querySelector('.add-btn').addEventListener('click', (e) => {
					const placeName = place.name;

					if (addedPlaces.includes(placeName)) {
						alert('이미 추가된 항목입니다.');
						return;
					}
					const parentContainer = document.querySelector('.new-panel');
					setTimeout(() => {
						parentContainer.scrollTop = parentContainer.scrollHeight;
					}, 0);
					const placeData = {
						placeId: place.placeId,
						travelMode: 'PUBLIC_TRANSIT'
					};
					savedPlaces.push(placeData);

					const placeElement = document.createElement('div');
					placeElement.classList.add('place-element');
					placeElement.style.display = 'flex';
					placeElement.style.alignItems = 'center';
					placeElement.textContent = placeName;
					const removeimg = document.createElement('img');
					removeimg.src = '/images/cart-x-fill.svg'; // 이미지 경로 설정
					removeimg.alt = 'Del'; // 대체 텍스트 설정
					removeimg.style.width = '15px'; // 이미지 크기 조절 (필요에 따라 설정)
					removeimg.style.height = '15px'; // 이미지 크기 조절 (필요에 따라 설정)




					removeimg.classList.add('remove-btn');
					removeimg.style.marginLeft = 'auto';
					removeimg.addEventListener('click', () => {
						placeCounter--;
						updatePlaceCounter();
						newPanel.removeChild(placeElement);

						const index = addedPlaces.indexOf(placeName);
						if (index !== -1) {
							addedPlaces.splice(index, 1);
						}
						const savedIndex = savedPlaces.findIndex(item => item.placeId === place.placeId);
						if (savedIndex !== -1) {
							savedPlaces.splice(savedIndex, 1);
						}
						place.removeMarker(places.indexOf(place));
						console.log(places.indexOf(place));
					});
					placeElement.appendChild(removeimg);

					const img = document.createElement('img');
					img.src = '/images/caret-down-fill.svg'; // 이미지 경로 설정
					img.alt = 'Down'; // 대체 텍스트 설정
					img.style.width = '15px'; // 이미지 크기 조절 (필요에 따라 설정)
					img.style.height = '15px'; // 이미지 크기 조절 (필요에 따라 설정)

					img.classList.add('down-btn');
					img.addEventListener('click', () => {
						const nextElement = placeElement.nextElementSibling;
						if (nextElement && nextElement.tagName === 'DIV' && nextElement.parentElement === newPanel) {
							newPanel.insertBefore(nextElement, placeElement);

							const index = addedPlaces.indexOf(placeName);
							if (index < addedPlaces.length - 1) {
								[savedPlaces[index], savedPlaces[index + 1]] = [savedPlaces[index + 1], savedPlaces[index]];
								[addedPlaces[index], addedPlaces[index + 1]] = [addedPlaces[index + 1], addedPlaces[index]];
							}
						}
					});

					placeElement.appendChild(img);

					const img2 = document.createElement('img');
					img2.src = '/images/caret-up-fill.svg'; // 이미지 경로 설정
					img2.alt = 'Up'; // 대체 텍스트 설정
					img2.style.width = '15px'; // 이미지 크기 조절 (필요에 따라 설정)
					img2.style.height = '15px'; // 이미지 크기 조절 (필요에 따라 설정)

					img2.classList.add('up-btn');
					img2.addEventListener('click', () => {
						const prevElement = placeElement.previousElementSibling;
						if (prevElement && prevElement.tagName === 'DIV' && prevElement.parentElement === newPanel) {
							newPanel.insertBefore(placeElement, prevElement);

							const index = addedPlaces.indexOf(placeName);
							if (index > 0) {
								[savedPlaces[index], savedPlaces[index - 1]] = [savedPlaces[index - 1], savedPlaces[index]];
								[addedPlaces[index], addedPlaces[index - 1]] = [addedPlaces[index - 1], addedPlaces[index]];
							}
						}
					});

					placeElement.appendChild(img2);

					newPanel.appendChild(placeElement);
					newPanel.style.display = 'block';

					addedPlaces.push(placeName);
					widget.addPlaceMarker1(place);


					// 카운트 갱신
					placeCounter++;
					updatePlaceCounter();
				});

			});

			document.querySelector('.make-btn').addEventListener('click', function() {
				$.ajax({
					url: "/sample/jeju_location",
					type: "POST",
					data: JSON.stringify(savedPlaces),
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					success: function(response) {
						var redirectUrl = response.redirectUrl;
						var requestData = response.requestData;
						console.log("이동할 URL:", redirectUrl);
						console.log("서버에서 받은 요청 데이터:", requestData);
						localStorage.setItem("requestData", JSON.stringify(requestData));
						window.location.href = "/sample/jeju_location";
					},
					error: function(errStatus) {
						console.log("에러 상태 코드내용 " + errStatus);
					}
				});
			});
		};







		// Index of next Place object to show in the POI list.
		let nextPlaceIndex = 0;

		// Fetch and show basic info for the next N places.
		const showNextPlaces = function(n) {
			const nextPlaces = widget.places.slice(nextPlaceIndex, nextPlaceIndex + n);
			if (nextPlaces.length < 1) {
				hideElement(showMoreButtonEl);
				return;
			}
			showMoreButtonEl.disabled = true;
			// Keep track of the number of Places calls that have not finished.
			let count = nextPlaces.length;
			for (let place of nextPlaces) {
				const processResult = function(place) {
					count--;
					if (count > 0) return;
					renderPlaceResults(nextPlaces, nextPlaceIndex);
					nextPlaceIndex += n;
					const hasMorePlacesToShow = nextPlaceIndex < widget.places.length;
					if (hasMorePlacesToShow || hasHiddenContent(placesPanelEl)) {
						showElement(showMoreButtonEl);
						showMoreButtonEl.disabled = false;
					} else {
						hideElement(showMoreButtonEl);
					}
				};
				widget.fetchPlaceDetails(place.placeId, [
					'name', 'types', 'geometry.location',
					'photo',
					'rating', 'user_ratings_total', 'price_level',
				], processResult);
			}
		};
		showNextPlaces(ND_NUM_PLACES_INITIAL);

		showMoreButtonEl.addEventListener('click', () => {
			placesPanelEl.classList.remove('no-scroll');
			showMoreButtonEl.classList.remove('sticky');
			showNextPlaces(ND_NUM_PLACES_SHOW_MORE);
		});
	}

	/** Initializes Search Input for the widget. */
	function initializeSearchInput() {
		const searchInputEl = widgetEl.querySelector('.place-search-input');
		widget.placeIdsToAutocompleteResults = new Map();

		// Set up Autocomplete on the search input.
		const autocomplete = new google.maps.places.Autocomplete(searchInputEl, {
			types: ['establishment'],
			fields: [
				'place_id', 'name', 'types', 'geometry.location', 'formatted_address', 'photo', 'url',
				'website', 'formatted_phone_number', 'opening_hours',
				'rating', 'user_ratings_total', 'price_level', 'review',
			],
			bounds: widget.mapBounds,
			strictBounds: true,
		});
		autocomplete.addListener('place_changed', () => {
			const place = autocomplete.getPlace();
			widget.placeIdsToAutocompleteResults.set(place.place_id, place);
			widget.selectPlaceById(place.place_id, /* panToMarker= */ true);
			searchInputEl.value = '';
		});
	}
}


function initMap() {
	new NeighborhoodDiscovery(CONFIGURATION);
}

document.addEventListener('DOMContentLoaded', function() {
	const closeButton = document.querySelector('.close-btn');
	const newPanel = document.querySelector('.new-panel');
	const arrowButton = document.querySelector('.arrow-button');

	closeButton.addEventListener('click', function() {
		newPanel.style.display = 'none';
	});

	arrowButton.addEventListener('click', function() {
		// 패널이 숨겨져 있을 때 보이게 하기

		newPanel.style.display = 'block';

	});
});






