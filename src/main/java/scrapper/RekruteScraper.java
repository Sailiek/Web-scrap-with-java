package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RekruteScraper implements WebScraper {

    // New method to get data instead of printing directly
    public List<String> getScrapedData() {
        List<String> jobDataList = new ArrayList<>();
        int numPageToScrap = 5;

        try {
            for (int pageNumber = 1; pageNumber <= numPageToScrap; pageNumber++) {
                String url = "https://www.rekrute.com/offres.html?s=1&p=" + pageNumber + "&o=1";
                Document doc = Jsoup.connect(url).get();
                Element ulElement = doc.select("div.page > div#fortopscroll > div.main.alt-main > div.section > div.container > div.row > div.col-md-9 > div.content-column > div.slide-block > ul").first();

                if (ulElement != null) {
                    for (Element li : ulElement.select("li")) {
                        Element sectionDiv = li.select("div > div.col-sm-10.col-xs-12 > div.section").first();
                        if (sectionDiv != null) {
                            StringBuilder jobInfo = new StringBuilder();

                            Element titleElement = sectionDiv.select("h2 > a").first();
                            jobInfo.append("Titre Annonce: ").append((titleElement != null) ? titleElement.text() : "No title found").append("\n");

                            Element introElement = sectionDiv.select("div.holder > div.info > span").first();
                            jobInfo.append("Intro Societe: ").append((introElement != null) ? introElement.text() : "No intro societe found").append("\n");

                            Element descriptionElement = sectionDiv.select("div.holder > div.info:nth-of-type(2) > span").first();
                            jobInfo.append("Description Offre: ").append((descriptionElement != null) ? descriptionElement.text() : "No description found").append("\n");

                            Element dateElement = sectionDiv.select("em.date > span").first();
                            jobInfo.append("Date de Publication: ").append((dateElement != null) ? dateElement.text() : "No date found").append("\n");

                            Element linkList = sectionDiv.select("div.holder > div.info > ul").first();
                            if (linkList != null) {
                                int index = 1;
                                for (Element linkItem : linkList.select("li")) {
                                    String label = switch (index) {
                                        case 1 -> "Secteur d'activité";
                                        case 2 -> "Fonction";
                                        case 3 -> "Experience requise";
                                        case 4 -> "Niveau d'étude";
                                        case 5 -> "Type de contrat";
                                        default -> "Additional Link";
                                    };
                                    String linkText = linkItem.select("a").eachText().stream().collect(Collectors.joining(", "));
                                    jobInfo.append(label).append(": ").append(linkText).append("\n");
                                    index++;
                                }
                            }
                            jobDataList.add(jobInfo.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobDataList;
    }

    @Override
    public void scrap() {
        // Can still use this method if you want to test directly in console
        getScrapedData().forEach(System.out::println);
    }
}
