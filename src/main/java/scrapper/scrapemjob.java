/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scrapper;

import java.util.ArrayList;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author der_u
 */
public class scrapemjob extends scrapper {
    @SuppressWarnings("SleepWhileInLoop")
    ArrayList<Offre> ScrapeMJob() throws Exception
    {
        ArrayList<Offre> offres = new ArrayList<Offre>();
        int nbrPages = 17;
        ArrayList<Integer> ex = new ArrayList<Integer>();
        ArrayList<Integer> exp = new ArrayList<Integer>();
        for (int i = 1; i <= nbrPages; ++i)
        {
            String url = "http://www.m-job.ma/recherche?page=" + i;
            Document doc = null;
            int currCount = 0;
            while (currCount < maxTries)
            {
                try
                {
                    doc = Jsoup.connect(url).get();
                    break;
                }
                catch (Exception e)
                {
                    System.out.println("TRYING TO HANDLE MAJOR EXCEPTION AT OUTER MJOB");
                    ++currCount;
                    Thread.sleep(10000);
                }
            }
            if (doc == null)
            {
                return offres;
            }
            
            //Sleep after request
            Random random = new Random();
            int randomTimeDev = random.nextInt((1000 - 100) + 1) + 100;
            int totalSleepTime = scraperSleepTime + randomTimeDev;
            System.out.print("Sleeping between jobs for " + totalSleepTime + "ms ...\n");
            Thread.sleep(totalSleepTime);

            Element offersContainer = doc.getElementsByClass("offers-boxes").get(0);
            Elements offersElements = offersContainer.getElementsByClass("offer-heading");
            int postnum = 0;
            for (Element offerHeading : offersElements)
            {
                try
                {
                    ++postnum;
                    String siteEntreprise = offerHeading.getElementsByClass("offer-img").get(0).getElementsByTag("a").get(0).attr("href");

                    String offerLink = offerHeading.getElementsByClass("offer-title").get(0).getElementsByTag("a").get(0).attr("href");

                    // Scrape each link for more info
                    Document offerDoc = null;
                    
                    int currCountinner = 0;
                    while (currCountinner < maxTries)
                    {
                        try
                        {
                            offerDoc = Jsoup.connect(offerLink).get();
                            break;
                        }
                        catch (Exception e)
                        {
                            System.out.println("TRYING TO HANDLE MAJOR EXCEPTION AT INNER MJOB");
                            ++currCountinner;
                            Thread.sleep(10000);
                        }
                    }
                    if (offerDoc == null)
                    {
                        return offres;
                    }

                    //Sleep after request
                    Random random1 = new Random();
                    int randomTimeDev1 = random1.nextInt((1000 - 100) + 1) + 100;
                    int totalSleepTime1 = scraperSleepTime + randomTimeDev1;
                    System.out.print("Sleeping between jobs for " + totalSleepTime1 + "ms ...\n");
                    Thread.sleep(totalSleepTime);

                    System.out.println("Link " + postnum + " : " + offerLink);

                    String titre = offerDoc.getElementsByClass("offer-title").get(0).text().trim();
                    String ville = offerDoc.getElementsByClass("location").get(0).text().trim();

                    Element rawDetailsUL = offerDoc.getElementsByClass("list-details").get(0);
                    String entreprise = rawDetailsUL.getElementsByTag("h3").get(0).text().trim();
                    String typeContrat = rawDetailsUL.getElementsByTag("h3").get(1).text().trim();
                    String salaire = rawDetailsUL.getElementsByTag("h3").get(2).text().trim();

                    Element rawBody = offerDoc.getElementsByClass("the-content").get(0);

                    String descriptionEntreprise = null;
                    String descriptionPoste = null;
                    String profile = null;
                    String secteurActivite = null;
                    String metier = null;
                    String experience = null;
                    String niveauEtudes = null;
                    String langues = null;

                    Elements contentDivs = rawBody.getElementsByTag("div");

                    for (int j = 0; j < contentDivs.size() - 2; ++j)
                    {
                        switch (rawBody.getElementsByTag("h3").get(j).text().trim())
                        {
                            case "Le recruteur :":
                                descriptionEntreprise = contentDivs.get(j + 1).text().trim();
                                break;
                            case "Poste à occuper :":
                                descriptionPoste = contentDivs.get(j + 1).text().trim();
                                break;
                            case "Profil recherché :":
                                profile = contentDivs.get(j + 1).text().trim();
                                break;
                            case "Secteur(s) d'activité :":
                                secteurActivite = contentDivs.get(j + 1).text().trim();
                                break;
                            case "Métier(s) :":
                                metier = contentDivs.get(j + 1).text().trim();
                                break;
                            case "Niveau d'expériences requis :":
                                experience = contentDivs.get(j + 1).text().trim();
                                break;
                            case "Niveau d'études exigé :":
                                niveauEtudes = contentDivs.get(j + 1).text().trim();
                                break;
                            case "Langue(s) exigée(s) :":
                                langues = contentDivs.get(j + 1).text().trim();
                                break;
                        }
                    }

                    Offre tempoffer = new Offre(titre,
                            offerLink,
                            "m-job.ma",
                            null,
                            null,
                            null,
                            siteEntreprise,
                            entreprise,
                            descriptionEntreprise,
                            descriptionPoste,
                            "Maroc",
                            ville,
                            secteurActivite,
                            metier,
                            typeContrat,
                            niveauEtudes,
                            null,
                            experience,
                            profile,
                            null,
                            null,
                            null,
                            null,
                            langues,
                            null,
                            salaire,
                            null,
                            null
                    );

                    offres.add(tempoffer);

                    /*System.out.println(offerLink + "\n" 
                            + titre + "\n" 
                            + ville + "\n" 
                            + entreprise + "\n" 
                            + typeContrat + "\n"
                            + salaire + "\n"
                            + descriptionEntreprise + "\n" 
                            + descriptionPoste + "\n" 
                            + profile + "\n" 
                            + secteurActivite + "\n" 
                            + metier + "\n" 
                            + experience + "\n" 
                            + niveauEtudes + "\n"
                            + langues + "\n"
                            + siteEntreprise + "\n\n\n");*/
                }
                catch (Exception e)
                {
                    System.out.println("Exception occured on post number " + postnum);
                    e.printStackTrace();
                    ex.add(postnum);
                    exp.add(i);
                }
            }
        }
        System.out.println("\n\n\n*********************EXCEPTION REPORT:****************************");

        for (int i = 0; i < exp.size(); ++i)
        {
                System.out.println("Post: " + ex.get(i) + " Page: "+ exp.get(i));
        }

        System.out.println("******************************************************************");
        return offres;
    }
}
