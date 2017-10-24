package de.dilello.www.snappypark.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.widget.ScrollView
import com.vansuita.materialabout.builder.AboutBuilder
import de.dilello.www.snappypark.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val view = AboutBuilder.with(this)
                .setPhoto(R.drawable.profile_picture)
                .setCover(R.drawable.profile_cover)
                .setName("Timothy Di Lello")
                .setSubTitle("Business Informatics Student")
                .setBrief("Give it all you have or don't bother trying.")
                .addWebsiteLink("https://www.dilello.de")
                .addInstagramLink("_tmo94_")
                .addLinkedInLink("timothy-di-lello-431b7b146")
                .addMoreFromMeAction("ItsT-Mo")
                .addFeedbackAction("info@dilello.de")
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .addGitHubLink("ItsT-Mo")
                .addFiveStarsAction()
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build()

        val layoutMatchParent = ViewGroup.LayoutParams.MATCH_PARENT
        val layoutParams =  ViewGroup.LayoutParams(
                layoutMatchParent,
                layoutMatchParent
        )

        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        scrollView.addView(view, layoutParams)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
