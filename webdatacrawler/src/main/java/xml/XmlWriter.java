package xml;

import domain.Offer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class XmlWriter {
    private static final String SERIALIZED = "products.xml";

    public static void writeToXmlFile(List<Offer> offers) {
        StringBuilder stringBuilder = new StringBuilder();

        String articleId;
        String productName;
        String brand;
        String color;
        String price;
        String initialPrice;
        String description;

        String startXmlString = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<offers>" +
                "";
        stringBuilder.append(startXmlString);

        for (Offer offer :
                offers) {
            articleId = offer.getArticleId();
            productName = offer.getProductName();
            brand = offer.getBrand();
            color = offer.getColor();
            price = offer.getPrice();
            initialPrice = offer.getInitialPrice();
            description = offer.getDescription();
            stringBuilder.append("" + " <offer>\n" + "  <articleId>").
                    append(articleId).append("</articleId>\n").append("  <name>").append(productName).
                    append("</name>\n").append("  <brand>").append(brand).append("</brand>\n").append("  <color>").
                    append(color).append("</color>\n").append("  <price>").append(price).append("</price>\n").
                    append("  <initial_price>").append(initialPrice).append("</initial_price\n>").append(" <description>").
                    append(description).append("<description>\n").append(" </offer>\n");
        }
        stringBuilder.append("</offers>");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(SERIALIZED))) {
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.append(stringBuilder.toString());
            bufferedWriter.flush();
            log.info("Writing..");
        } catch (IOException e) {
            log.error("Cant write to xml file");
        }
    }
}

