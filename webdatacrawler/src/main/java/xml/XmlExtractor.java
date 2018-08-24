package xml;

import domain.Offer;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class XmlExtractor {


    public static void extract() {
        List<Offer> offers = new ArrayList<>();

        try {
            String keyWord = "boots";
            String ABOUT_YOU_URL = "http://www.aboutyou.de/suche?term=";
            Document searchByKeyWord = Jsoup.connect(ABOUT_YOU_URL + keyWord).get();
            Elements searchResultPage = searchByKeyWord.getElementsByClass("styles__tile--2s8XN col-sm-6 col-md-4 col-lg-4");
            for (Element element :
                    searchResultPage
            ) {
                Elements linkToProductPage = element.getElementsByTag("a");
                String absHref = linkToProductPage.attr("abs:href");

                Document productPage = Jsoup.connect(absHref).get();
                Elements articleId = productPage.getElementsByClass("styles__articleNumber--1UszN");
                Elements description = productPage.getElementsByClass("styles__textElement--3QlT_");
                Elements productName = element.getElementsByClass("styles__productName--2z0ZU");
                Elements brand = element.getElementsByClass("styles__brandName--2XS22");
                Elements color = productPage.getElementsByClass("styles__title--UFKYd styles" +
                        "__isHoveredState--2BBt9");
                Elements price = element.getElementsByClass("productPrices prices__normal--3SBAf");
                Elements initialPrice = element.getElementsByClass("prices__strike--Htmqx");

                Offer offer = new Offer();
                offer.setArticleId(articleId.text());
                offer.setProductName(productName.text());
                offer.setBrand(brand.text());
                offer.setColor(color.text());
                offer.setPrice(price.text());
                offer.setInitialPrice(initialPrice.text());
                offer.setDescription(description.text());
                offers.add(offer);
            }
        } catch (Exception e) {
            log.error("Can't extract you xml file!");
        }
        System.out.println(offers.size());
        XmlWriter.writeToXmlFile(offers);
        System.out.println("Amount of extracted products: " + offers.size());
    }
}