package org.polar.template.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class FirebaseConfig {

    @PostConstruct
    fun init() {
        val serviceAccount = FileInputStream("src/main/resources/serviceAccountKey.json")

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        if (FirebaseApp.getApps().isNotEmpty()) return
        FirebaseApp.initializeApp(options)
    }
}