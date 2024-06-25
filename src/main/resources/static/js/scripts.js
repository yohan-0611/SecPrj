/*!
* Start Bootstrap - Modern Business v5.0.7 (https://startbootstrap.com/template-overviews/modern-business)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-modern-business/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project
document.addEventListener('DOMContentLoaded', function() {
    const cards = document.querySelectorAll('.card');

    cards.forEach(card => {
        card.addEventListener('click', function() {
            const title = this.querySelector('.card-title').textContent;
            const description = this.querySelector('.card-text').textContent;
            const imageSrc = this.querySelector('img').src;
            const data = this.querySelector('.data-title').textContent;
            const content = this.querySelector('.data-content').textContent;

            document.getElementById('locationModalLabel').textContent = title;
            document.getElementById('modalDescription').textContent = description;
            document.getElementById('modalImage').src = imageSrc;
            document.getElementById('modalData').textContent = data;
            document.getElementById('modalContent').textContent = content;

            const locationModal = new bootstrap.Modal(document.getElementById('locationModal'));
            locationModal.show();
        });
    });
});

