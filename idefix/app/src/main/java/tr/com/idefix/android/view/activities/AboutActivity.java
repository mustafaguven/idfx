package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import tr.com.idefix.android.R;

/**
 * Created by mustafaguven on 06.11.2015.
 */
public class AboutActivity extends BaseActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;

  @Bind(R.id.lblVisionTitle) TextView lblVisionTitle;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about_activity);
    ButterKnife.bind(this);

    String html = "<b>idefix ne?</b><div>idefix,"
        + " Türkiye'de kültür ürünleri denilince akla gelen ilk ve en gelişkin online yayın olmak"
        + " üzere tasarlandı. 1996 yılının sonunda kuruldu. Şu an Türkiye'nin en büyük ve en "
        + "yenilikçi online kitabevi olarak yayın hayatına devam etmektedir. "
        + "<br/><br/><b>idefix'in "
        + "kilometre taşları<br/><br/>idefix Sanal Kitap Fuarı</b><br/>Birincisi 2003'te "
        + "düzenlenen "
        + "idefix Sanal Kitap Fuarı 11 senedir okurların büyük ilgisiyle aralıksız olarak devam "
        + "ediyor. Sanal Kitap Fuarı'da yapılan sayısız etkinliğin yanı sıra 1500'ün üzerinde "
        + "yayınevinin 100.000 farklı kitabı %70'ye varan indirimlerle okurlarla buluşuyor. Sanal"
        + " Kitap Fuarı son 3 senedir en fazla ziyaretçi sayısı ile Türkiye'nin en büyük kitap "
        + "fuarı ünvanını da elinde bulundurmaktadır. <br/><br/><b>Yayınevi "
        + "Siteleri</b><br/>2006'dan bu"
        + " yana; aralarında NTV Yayınları, Can Yayınları, Yapı Kredi Yayınları, İş Bankası "
        + "Kültür Yayınları gibi büyük yayınevlerinin de bulunduğu 50'nin üzerinde yayınevinin "
        + "sitesini idefix işletiyor. <br/><br/><b>Sabitfikir.com<br/></b>Edebiyat dünyasının "
        + "yıllardır "
        + "tartışılagelen sorunlarını taze bir bakış açısıyla ele alan, edebiyatı güncel "
        + "yaşamımıza dahil ederek, sıkıcı ve zor anlaşılan yüzünden kurtaran idefix'in edebiyat "
        + "dergisi Sabitfikir.com 2009'da online olarak yayın hayatına başladı. "
        + "<br/><br/><b>E-Kitap<br/></b>Nisan 2010'da TTGV ve TÜBİTAK işbirliğiyle Türkiye'nin ilk "
        + "e-kitap platformunu hayata geçirdi. İlk olarak Adobe DRM destekli cihazları "
        + "destekleyen idefix, sırası ile iPad, iPhone ve Android işletim sistemli cihazlara da "
        + "destek vermeye başladı. <br/><br/><b>SabitFikir Dergisi</b><br/>Okurlar tarafından "
        + "büyük bir "
        + "ilgiyle takip edilen Sabit Fikir dergisi 2011'in Mart ayından itibaren basılı olarak "
        + "da okurlarla buluştu. Sabit Fikir dergisi şu anda 30.000 adet basılarak idefix "
        + "siparişleriyle birlikte ücretsiz olarak gönderilmektedir. <br/><br/><b>Prefix.com"
        + ".tr<br/></b>Ağustos 2011'de Anadolu'daki kitapçıların kitaba erişim sorununa köklü bir "
        + "çözüm getiren B2B kitap dağıtım platformu olan idefix'in kardeş markası Prefix.com.tr "
        + "kuruldu. <br/><br/><b>Sözünü Sakınmadan<br/></b>idefix, Sabit Fikir ve İstanbul Modern"
        + " Müzesi"
        + " işbirliğinde gerçekleştirilen Sözünü Sakınmadan söyleşileri ile usta eleştirmenler "
        + "Semih Gümüş ve Ömer Türkeş'in moderatörlüğünde Murathan Mungan'dan Elif Şafak'a, Hakan"
        + " Günday'dan Selim İleri'ye kadar 13 değerli yazarı ve yüzlerce edebiyatseveri konuk "
        + "etti. <br/><br/><b>idefix Yazar Ormanları</b><br/>Ormanlarımız bizim geleceğimiz, "
        + "onları "
        + "korumak da kitapseverler olarak çevreye borcumuz. İstiyoruz ki biz okurken doğal "
        + "ormanlarımız yok olmasın, yeşiller gelecek nesillere kalsın. İşte bu sebeple, "
        + "idefix'ten her sene satılan kitaplar için kesilen ağaçları doğaya geri kazandırıyoruz."
        + " Her sene gerçekleştirdiğimiz bu ormanlarımıza da idefix üyelerinin oylarıyla "
        + "belirlediğimiz yazarlarımızın isimlerini veriyoruz</div></pre>";

    lblVisionTitle.setText(Html.fromHtml(html), TextView.BufferType.SPANNABLE);
    deviceUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.bg_red_toolbar_start));
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_white_back);
    toolbar.setNavigationOnClickListener(v -> finish());
  }
}