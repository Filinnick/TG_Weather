import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    public static void main(String[] args) {

        //Подключаем API телеграма
        ApiContextInitializer.init();
        TelegramBotsApi telegram = new TelegramBotsApi();

        //Активируем бота (создаем экземпляр бота и ловим любую ошибку от API телеграм)
        Bot WeatherBot = new Bot();
        try {
            telegram.registerBot(WeatherBot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }

}
