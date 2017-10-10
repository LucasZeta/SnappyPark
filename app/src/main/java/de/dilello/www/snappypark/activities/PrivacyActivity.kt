package de.dilello.www.snappypark.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import de.dilello.www.snappypark.R
import de.dilello.www.snappypark.services.FileService

class PrivacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val textBody = findViewById<TextView>(R.id.txtBody)
        val privacyText = FileService.readHtmlFile("privacy_policy.html", baseContext)
        textBody.setText(Html.fromHtml(privacyText, Html.FROM_HTML_MODE_LEGACY))
        textBody.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
