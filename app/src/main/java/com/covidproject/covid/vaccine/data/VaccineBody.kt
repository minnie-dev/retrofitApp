package com.covidproject.covid.vaccine.data

import com.google.gson.annotations.SerializedName

data class VaccineBody(
    @SerializedName("page") val page:Int, // 페이지 인덱스
    @SerializedName("perPage") val perPage:Int, // 페이지 사이즈
    @SerializedName("totalCount") val totalCount:Int, // 데이터 전체 개수
    @SerializedName("currentCount") val currentCount:Int, // 현재 검색된 데이터 수
    @SerializedName("data") val data:List<Vaccine> // 현황 데이터
){
    override fun toString(): String {
        return "$data\n\n"+
                "currentCount : $currentCount\n"+
                "page : $page\n"+
                "perPage : $perPage\n"+
                "totalCount : $totalCount\n"
    }
}

data class Vaccine(
    @SerializedName("id") val id:Int, // 예방 접종 센터 고유 식별자
    @SerializedName("centerName") val centerName:String, // 예방 접종 센터 명
    @SerializedName("sido") val sido:String, // 시도
    @SerializedName("sigungu") val sigungu:String, // 시군구
    @SerializedName("facilityName") val facilityName:String, // 시설명
    @SerializedName("zipCode") val zipCode:String, // 우편번호
    @SerializedName("address") val address:String, // 주소
    @SerializedName("lat") val lat:String, // 좌표 위도
    @SerializedName("lng") val lng:String, // 좌표 경도
    @SerializedName("createdAt") val createdAt:String,
    @SerializedName("updatedAt") val updatedAt:String,
    @SerializedName("centerType") val centerType:String, // 예방 접종 센터 유형
    @SerializedName("org") val org:String, // 운영기관
    @SerializedName("phoneNumber") val phoneNumber:String // 사무실 전화번호
){
    override fun toString(): String {
        return "Vaccine :[\n"+
                "   id : ${id}\n"+
                "   centerName : ${centerName}\n"+
                "   sido : ${sido}\n"+
                "   sigungu : ${sigungu}\n"+
                "   facilityName : ${facilityName}\n"+
                "   zipCode : ${zipCode}\n"+
                "   address : ${address}\n"+
                "   lat : ${lat}\n"+
                "   lng : ${lng}\n"+
                "   createdAt : ${createdAt}\n"+
                "   updatedAt : ${updatedAt}\n"+
                "   centerType : ${centerType}\n"+
                "   org : ${org}\n"+
                "   phoneNumber : ${phoneNumber}\n\n"

    }
}
