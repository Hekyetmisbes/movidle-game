package com.erciyes.movidlegame;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MovidleController {
    @FXML
    private Label movidle;
    @FXML
    private Button restartBtn;
    @FXML
    private Label skor;
    @FXML
    private Button btnChk;
    @FXML
    private TextField guessText;
    @FXML
    private Label lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11, lbl12, lbl13, lbl14, lbl15, lbl16, lbl17, lbl18, lbl19, lbl20, lbl21, lbl22, lbl23, lbl24, lbl25, lbl26, lbl27, lbl28, lbl29, lbl30;

    //Okunacak dosya ismi ve kesilecek biçimi tanımlıyoruz.
    public String csvDosya = "imdb_top_250.csv";
    public String csvSplitBy = ";";

    //Filmleri bir listeye kaydediyoruz ve bu listeden rastgele bir film seçiyoruz.
    List<Film> filmListesi = FilmleriOku(csvDosya, csvSplitBy);
    Film rastgeleFilm = rastgeleFilmSec(filmListesi);

    //Oyunun bitip bitmediğini kontrol etmek için bir değişken oluşturuyoruz.
    private boolean gameFinished = false;

    //Oyun içinde bulunan labelları burada tanımlıyoruz.
    public List<Label> listeOlustur() {
        List<Label> lblList = new ArrayList<>();
        lblList.add(lbl1);

        lblList.add(lbl2);

        lblList.add(lbl3);

        lblList.add(lbl4);

        lblList.add(lbl5);

        lblList.add(lbl6);

        lblList.add(lbl7);

        lblList.add(lbl8);

        lblList.add(lbl9);

        lblList.add(lbl10);

        lblList.add(lbl11);

        lblList.add(lbl12);

        lblList.add(lbl13);

        lblList.add(lbl14);

        lblList.add(lbl15);

        lblList.add(lbl16);

        lblList.add(lbl17);

        lblList.add(lbl18);

        lblList.add(lbl19);

        lblList.add(lbl20);

        lblList.add(lbl21);

        lblList.add(lbl22);

        lblList.add(lbl23);

        lblList.add(lbl24);

        lblList.add(lbl25);

        lblList.add(lbl26);

        lblList.add(lbl27);

        lblList.add(lbl28);

        lblList.add(lbl29);

        lblList.add(lbl30);
        return lblList;
    }

    //Oyunda doğru bildiğimiz bilgiler için 1 puan almaktayız.
    public int oyunSkor = 0;

    //Oyunun bitip bitmediğini kontrol eden sayacı oluşturuyoruz.
    public int sayac = 1;

    //Oyunu kazanıp kazanmadığını gösterebilmek için doğru sayacı oluşturuyoruz.
    public int dogruSayisi;

    //Bu bölümde oyunu farklı göstermesi amacıyla "Movidle" yazımızı rengarenk dolduruyoruz.
    private Timeline timeline;
    private List<Color> colors;
    private int currentColorIndex;

    public void initialize() {
        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);

        Duration duration = Duration.seconds(2);

        currentColorIndex = 0;

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        createKeyFrames(duration);

        timeline.play();
    }

    private void createKeyFrames(Duration duration) {
        for (int i = 0; i < colors.size(); i++) {
            Color startColor = colors.get(i);
            Color endColor = colors.get((i + 1) % colors.size());

            KeyFrame keyFrame = new KeyFrame(duration.multiply(i), new KeyValue(movidle.textFillProperty(), startColor));
            timeline.getKeyFrames().add(keyFrame);

            keyFrame = new KeyFrame(duration.multiply(i + 1), new KeyValue(movidle.textFillProperty(), endColor));
            timeline.getKeyFrames().add(keyFrame);
        }
    }

    //Kazanıp kaybettiğimizi veya aradığımız filmin bulunmadığını gösteren mesaj kutusu nesnesini oluşturuyoruz.
    Alert alert;

    //Kontrol ettiğimiz butona bastığımızda olacakları tanımlıyoruz.
    @FXML
    public void clickedBtn(ActionEvent event) {
        dogruSayisi = 0;

        alert= new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);

        //Kullanıcının girdiği filmi listede arıyoruz.
        Film kullaniciFilm = kullaniciFilmiBul(filmListesi, guessText.getText());

        //Eğer kullanıcının girdiği film bulunamadıysa uyarı çıkarıyoruz.
        if (kullaniciFilm == null) {
            alert.setTitle("Uyarı");
            alert.setHeaderText("Böyle bir film bulunamadı.");
            alert.setContentText("Başka bir film adı girmeyi deneyin.");
            alert.showAndWait();
        }
        //Eğer film bulunduysa bu bloktan devam ediyoruz.
        else {
            //Her seferinde bir sonraki satırdaki label üzerinden işlem olacağından ona uygun bir formül kullanıyoruz.
            Label filmAdi = listeOlustur().get((6 * sayac - 5) - 1);
            Label filmYili = listeOlustur().get((6 * sayac - 5));
            Label filmTuru = listeOlustur().get((6 * sayac - 5) + 1);
            Label filmUlkesi = listeOlustur().get((6 * sayac - 5) + 2);
            Label filmYonetmeni = listeOlustur().get((6 * sayac - 5) + 3);
            Label filmYildizi = listeOlustur().get((6 * sayac - 5) + 4);

            List<Node> filmler = Arrays.asList(filmAdi, filmYili, filmTuru, filmUlkesi, filmYonetmeni, filmYildizi);

            int baslangicZaman = 1500;

            //Görüntünün hoş olması adına labelların gelişini geçişli yapıyoruz.
            for (int i = 0; i < filmler.size(); i++) {
                FadeTransition ft = new FadeTransition(Duration.millis(baslangicZaman + i * 250), filmler.get(i));
                filmler.get(i).setVisible(true);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.setCycleCount(1);
                ft.setAutoReverse(false);
                ft.play();
            }

            //Kullanıcının girdiği filmin özelliklerini kullanıcıya gösteriyoruz.
            filmAdi.setText(kullaniciFilm.filmAdi);
            filmYili.setText(kullaniciFilm.yil);
            filmTuru.setText(kullaniciFilm.tur);
            filmUlkesi.setText(kullaniciFilm.ulke);
            filmYonetmeni.setText(kullaniciFilm.yonetmen);
            filmYildizi.setText(kullaniciFilm.yildiz);

            //Kullanıcının bildiği özelliğe göre renk değiştiren labellar ve doğru sayısındaki değişimi ayarlıyoruz.
            if (kullaniciFilm.getFilmAdi().equalsIgnoreCase(rastgeleFilm.getFilmAdi())) {
                filmAdi.setBackground(new Background(new BackgroundFill(Color.web("#05C46B"), CornerRadii.EMPTY, Insets.EMPTY)));
                dogruSayisi++;
                oyunSkor++;
            } else {
                filmAdi.setBackground(new Background(new BackgroundFill(Color.web("#D90329"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            if (kullaniciFilm.getYil().equals(rastgeleFilm.getYil())) {
                filmYili.setBackground(new Background(new BackgroundFill(Color.web("#05C46B"), CornerRadii.EMPTY, Insets.EMPTY)));
                dogruSayisi++;
                oyunSkor++;
            } else {
                filmYili.setBackground(new Background(new BackgroundFill(Color.web("#D90329"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            if (kullaniciFilm.getTur().equalsIgnoreCase(rastgeleFilm.getTur())) {
                filmTuru.setBackground(new Background(new BackgroundFill(Color.web("#05C46B"), CornerRadii.EMPTY, Insets.EMPTY)));
                dogruSayisi++;
                oyunSkor++;
            } else {
                filmTuru.setBackground(new Background(new BackgroundFill(Color.web("#D90329"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            if (kullaniciFilm.getUlke().equalsIgnoreCase(rastgeleFilm.getUlke())) {
                filmUlkesi.setBackground(new Background(new BackgroundFill(Color.web("#05C46B"), CornerRadii.EMPTY, Insets.EMPTY)));
                dogruSayisi++;
                oyunSkor++;
            } else {
                filmUlkesi.setBackground(new Background(new BackgroundFill(Color.web("#D90329"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            if (kullaniciFilm.getYonetmen().equalsIgnoreCase(rastgeleFilm.getYonetmen())) {
                filmYonetmeni.setBackground(new Background(new BackgroundFill(Color.web("#05C46B"), CornerRadii.EMPTY, Insets.EMPTY)));
                dogruSayisi++;
                oyunSkor++;
            } else {
                filmYonetmeni.setBackground(new Background(new BackgroundFill(Color.web("#D90329"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            if (kullaniciFilm.getYildiz().equalsIgnoreCase(rastgeleFilm.getYildiz())) {
                filmYildizi.setBackground(new Background(new BackgroundFill(Color.web("#05C46B"), CornerRadii.EMPTY, Insets.EMPTY)));
                dogruSayisi++;
                oyunSkor++;
            } else {
                filmYildizi.setBackground(new Background(new BackgroundFill(Color.web("#D90329"), CornerRadii.EMPTY, Insets.EMPTY)));
            }

            //Oyundaki ilerlememizi kontrol eden sayacı her adımda bir defa daha oynatıyoruz.
            sayac++;

            //Skorumuzu ekrana yazdırıyoruz.
            skor.setText("Skorunuz: " + oyunSkor);

            //Tüm özellikler eşleştiğinde oyunun bittiğini belirtmek için bir if bloğu oluşturuyoruz.
            if (dogruSayisi == 6) {
                alert.setTitle("Tebrikler Bildiniz!");
                messageBoxCevap();
            }

            //Sayaç 5 defa çalıştıktan sonra 6. adımda oyunun bittiğini kullanıcıya bildiriyoruz.
            if (sayac == 6 && dogruSayisi != 6) {
                alert.setHeaderText("Maalesef Bilemediniz.\n");
                messageBoxCevap();
            }

            guessText.clear();
        }
    }

    //Oyunun sonundaki messagebox için genel bir metod oluşturuyoruz.
    public void messageBoxCevap(){
        gameFinished = true;
        btnChk.setDisable(true);
        alert.setContentText("Doğru Cevap: " + rastgeleFilm.filmAdi + "\nSkorunuz: " + oyunSkor);
        alert.showAndWait();
    }

    //Okuduğumuz dosyadaki verileri bir listeye kaydediyoruz.
    public static List<Film> FilmleriOku(String csvDosya, String csvSplitBy) {
        List<Film> filmListesi = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvDosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] veriler = satir.split(csvSplitBy);
                String filmAdi = veriler[1];
                String yil = veriler[2];
                String tur = veriler[3];
                String ulke = veriler[4];
                String yonetmen = veriler[5];
                String yildiz = veriler[6];

                Film film = new Film(filmAdi, yil, tur, ulke, yonetmen, yildiz);
                filmListesi.add(film);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filmListesi;
    }

    //Kullanıcının ismini girdiği filmin verilerini dosyada arıyoruz.
    public static Film kullaniciFilmiBul(List<Film> filmListesi, String kullaniciFilmIsmi) {
        for (Film film : filmListesi) {
            if (film.getFilmAdi().equalsIgnoreCase(kullaniciFilmIsmi)) {
                return film;
            }
        }
        return null;
    }

    //Bilgisayar maksimum liste boyutundaki rastgele bir sayı ile listeden bir film seçiyor
    public static Film rastgeleFilmSec(List<Film> filmListesi) {
        Random random = new Random();
        int rastgeleNumara = random.nextInt(1, 250) + 1;
        return filmListesi.get(rastgeleNumara);
    }

    //Filmin özelliklerini çekiyor ve get bloklaryla uygulama içinde dönmesini sağlıyoruz.
    public static class Film {
        private String filmAdi;
        private String yil;
        private String tur;
        private String ulke;
        private String yonetmen;
        private String yildiz;

        public Film(String filmAdi, String yil, String tur, String ulke, String yonetmen, String yildiz) {
            this.filmAdi = filmAdi;
            this.yil = yil;
            this.tur = tur;
            this.ulke = ulke;
            this.yonetmen = yonetmen;
            this.yildiz = yildiz;
        }

        public String getFilmAdi() {
            return filmAdi;
        }

        public String getYil() {
            return yil;
        }

        public String getTur() {
            return tur;
        }

        public String getUlke() {
            return ulke;
        }

        public String getYonetmen() {
            return yonetmen;
        }

        public String getYildiz() {
            return yildiz;
        }
    }

    //Oyunu sıfırlayıp tekrar oynanmasını sağlıyoruz.
    @FXML
    public void clickedrstBtn(ActionEvent event) {
        listeOlustur().forEach(label -> label.setVisible(false));
        guessText.clear();
        oyunSkor = 0;
        skor.setText("Skorunuz: " + oyunSkor);
        dogruSayisi = 0;
        sayac = 1;
        rastgeleFilm = rastgeleFilmSec(filmListesi);
        btnChk.setDisable(false);
        gameFinished = false;
    }

    //TextFielda film adını girdikten sonra entera basınca click eventinin çalışmasını tetikliyoruz.
    @FXML
    public void pressedEnter(KeyEvent keyEvent) {
        if (!gameFinished && keyEvent.getCode() == KeyCode.ENTER) {
            gameFinished = false;
            clickedBtn(new ActionEvent());
        }
    }
}