package com.covidproject.covid.model.data

import com.tickaroo.tikxml.annotation.*

@Xml (name="response")
data class CovidTest(
    @Element
    val header : Header,
    @Element
    val body : Body,
)

@Xml(name="header")
data class Header(
    @PropertyElement
    val resultCode : Int,
    @PropertyElement
    val resultMsg :String,
)

@Xml(name = "body")
data class Body(
    @PropertyElement
    val pageNo:Int, // 페이지 번호
    @PropertyElement
    val numOfRows:Int, // 한 페이지 결과 수
    @PropertyElement
    val totalCount:Int, // 데이터 총 개수
    @Element
    val items: Items, // 현황 데이터
)

@Xml(name = "items")
data class Items(
    @Element(name = "item")
    val item:List<Item> // 세부항목
)

@Xml
data class Item(
    @PropertyElement
    val sidoNm:String?, // 시도명
    @PropertyElement
    val sgguNm:String?, // 시군구명
    @PropertyElement
    val yadmNm:String?, // 기관명
    @PropertyElement
    val hospTyTpCd:String?, // 선정유형
    @PropertyElement
    val telno:String?, // 전화번호
    @PropertyElement
    val adtFrDd:String?, // 운영가능일자
    @PropertyElement
    val spclAdmTyCd:String? // 구분코드
){
    override fun toString(): String {

        return "CovidTest :[\n"+
                "   sidoNm : ${sidoNm}\n"+
                "   sgguNm : ${sgguNm}\n"+
                "   yadmNm : ${yadmNm}\n"+
                "   hospTyTpCd : ${hospTyTpCd}\n"+
                "   telno : ${telno}\n"+
                "   adtFrDd : ${adtFrDd}\n"+
                "   spclAdmTyCd : ${spclAdmTyCd}\n\n"
    }
}
