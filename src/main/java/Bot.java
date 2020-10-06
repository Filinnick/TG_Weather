import com.ibm.icu.text.Transliterator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    public static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
    private long chatId;

    @Override
    public void onUpdateReceived(Update update) {

        update.getUpdateId(); // определяем ID пользователя, который пишет боту
        chatId = update.getMessage().getChatId();

        //настраиваем вывод сообщения в необходимый диалог
        SendMessage sendMessage = new SendMessage().setChatId(chatId);

        //Получаем входящее сообщение и выводим ответ
        sendMessage.setText(outputMsg(update.getMessage().getText()));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    public String outputMsg(String msg) {

        //В случае, если пользователь вводит город кирилицей - транслитерация в латинские символы для вставки в адресную строку
        //Подключена библиотека в Maven - com.ibm.icu...
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        String transliterateMsg = toLatinTrans.transliterate(msg);
        Weather weather = new Weather(transliterateMsg);

        if(msg.equals("/start")) {return "Введите город. если при вводе ничего не происходит, попробуйте ввести город латиницей."; }

        return weather.getOutTempString();

    }

    @Override
    public String getBotUsername() {
        return "@ForsakenStarBot";
    }

    @Override
    public String getBotToken() {
        return "1300223694:AAFHp8SuQACMSgYMjE-tbQzw5WhCqmeqlBc";
    }
}
