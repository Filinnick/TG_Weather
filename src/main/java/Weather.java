import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Weather {

    private String outTempString = "";
    private Document document;

    public Weather(String city) {
        connect(city);
    }

    private void connect(String city) {

        String urlYandexWeather;
        urlYandexWeather = "https://yandex.ru/pogoda/" + city; //формирование адресной строки для парсинга страницы погоды в городе
        try {
            document = Jsoup.connect(urlYandexWeather).get();
            formWeeklyTemp();
        } catch (IOException e) {
            e.printStackTrace();
            outTempString = "Этот бот работает с помощью сервиса Яндекс.Погода" + "\n" + "Проверьте, существует ли страница погоды: " + urlYandexWeather;
        }

    }

    public void formWeeklyTemp() {

        int currentYandexDay = 4; //Текущий день - 4, так считает яндекс погода, вчера - 3, завтра - 5

        //получаем данные о датах
        Elements dates = document.getElementsByClass("time forecast-briefly__date");

        //получаем данные о днях
        Elements dayNames = document.getElementsByClass("forecast-briefly__name");

        //Получаем данные о дневной погоде за месяц
        Elements dayTemps = document.getElementsByClass("temp forecast-briefly__temp forecast-briefly__temp_day");

        //Получаем данные о ночной погоде за месяц
        Elements nightTemps = document.getElementsByClass("temp forecast-briefly__temp forecast-briefly__temp_night");

        //Получаем данные о погодных условиях (ясно, облачно, пасмурно, дождь и т.д.)
        Elements dayCond = document.getElementsByClass("forecast-briefly__condition");

        for (currentYandexDay = 4; currentYandexDay<=10; currentYandexDay++) {

            //формирование строки выдачи
            outTempString += dates.get(currentYandexDay).text() +
                    " " + dayNames.get(currentYandexDay).text()  +
                    "  " + dayTemps.get(currentYandexDay).text().replaceAll("днём","")  +
                    "/" + nightTemps.get(currentYandexDay).text().replaceAll("ночью","") +
                    " " + dayCond.get(currentYandexDay).text() + "\n";

        }

    }

    public String getOutTempString() {
        return outTempString;
    }


}
