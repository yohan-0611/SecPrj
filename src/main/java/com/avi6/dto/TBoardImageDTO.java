package com.avi6.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TBoardImageDTO {

   private Long inum;

   private String uuid;

   private String imgName;

   private String path;
   
   public String getImageURL() {
      // 이 URL 정보는 업로드한 이미지를 볼 수 있도록 URL 정보를 담고 있음.
      // 그런데 이 정보를 주고 받을때 기본적인 en/decoding 을 통해서 처리하도록 함. URIEncoder.encod...decod...

      try {
         return URLEncoder.encode(path + "/" + uuid + "_" + imgName, "UTF-8");
      } catch (UnsupportedEncodingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }

   public String getThumImageURL() {
      // 이 URL 정보는 업로드한 이미지를 볼 수 있도록 URL 정보를 담고 있음.
      // 그런데 이 정보를 주고 받을때 기본적인 en/decoding 을 통해서 처리하도록 함. URIEncoder.encod...decod...

      try {
         return URLEncoder.encode(path + "/" + "thum" + uuid + "_" + imgName, "UTF-8");
      } catch (UnsupportedEncodingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }

}