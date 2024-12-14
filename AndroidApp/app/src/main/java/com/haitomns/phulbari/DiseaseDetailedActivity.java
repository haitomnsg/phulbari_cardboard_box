package com.haitomns.phulbari;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DiseaseDetailedActivity extends AppCompatActivity {

    private ImageView DiseaseImageView;
    private TextView DiseaseNameTextView;
    private TextView DiseaseReasonTextView;
    private TextView DiseaseCureTextView;
    private TextView DiseasePreventionTextView;

    private Button DiseaseCureVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detailed);

        DiseaseImageView = findViewById(R.id.disease_image);
        DiseaseNameTextView = findViewById(R.id.disease_name);
        DiseaseReasonTextView = findViewById(R.id.reason_text);
        DiseaseCureTextView = findViewById(R.id.cure_text);
        DiseasePreventionTextView = findViewById(R.id.prevention_text);

        DiseaseCureVideo = findViewById(R.id.video_button);
        DiseaseCureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeUrl = "https://youtu.be/Wyt6rsFGPsc?si=J_rVZA3u96Xv-d6j";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));

                intent.setPackage("com.google.android.youtube");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });

        String diseaseName = getIntent().getStringExtra("disease_name");
        loadDiseaseDetails(diseaseName);
    }

    private void loadDiseaseDetails(String diseaseName) {
        if (diseaseName.equals("EarlyBlight")) {
            DiseaseImageView.setImageResource(R.drawable.early_blight);
            DiseaseNameTextView.setText("Early Blight (सुरुवाती खैरो रोग)");
            DiseaseReasonTextView.setText("Caused by the fungus, it survives in warm, humid conditions and infects older leaves first.");
            DiseaseCureTextView.setText("- Apply fungicides containing chlorothalonil or copper-based solutions.\n" + "- Remove and destroy infected leaves to limit spread.");
            DiseasePreventionTextView.setText("- Use disease-resistant varieties and rotate crops.\n" + "- Avoid overhead watering to keep leaves dry.");
        } else if (diseaseName.equals("LateBlight")) {
            DiseaseImageView.setImageResource(R.drawable.late_blight);
            DiseaseNameTextView.setText("Late Blight (अन्तिम खैरो रोग)");
            DiseaseReasonTextView.setText("Caused by the fungus, it thrives in cool, wet conditions and infects younger leaves first.");
            DiseaseCureTextView.setText("- Apply systemic fungicides like metalaxyl or mancozeb.\n" + "- Remove and destroy infected plant parts immediately.");
            DiseasePreventionTextView.setText("- Use certified, disease-free seeds or plants.  \n" + "- Ensure proper spacing for air circulation and avoid waterlogged soil.");
        } else if (diseaseName.equals("Powdery")) {
            DiseaseImageView.setImageResource(R.drawable.powdery);
            DiseaseNameTextView.setText("Powdery Mildew (सेतो धुलो रोग )");
            DiseaseReasonTextView.setText("Caused by various fungi, it develops in dry, warm weather with high humidity.");
            DiseaseCureTextView.setText("- Spray sulfur-based fungicides or neem oil.\n" + "- Remove and discard affected leaves to halt fungal spread.");
            DiseasePreventionTextView.setText("- Ensure good air circulation and avoid overcrowding plants.\n" + "- Water plants early in the day to keep foliage dry overnight.");
        } else if (diseaseName.equals("Rust")) {
            DiseaseImageView.setImageResource(R.drawable.rust);
            DiseaseNameTextView.setText("Rust (खिया रोग)");
            DiseaseReasonTextView.setText("Caused by fungal pathogens, it thrives in moist conditions and overwinters on debris.");
            DiseaseCureTextView.setText("- Apply fungicides like sulfur or copper oxychloride.\n" + "- Prune infected leaves and clean up plant debris.");
            DiseasePreventionTextView.setText("- Use resistant plant varieties and maintain clean gardening tools.\n" + "- Space plants properly and avoid overhead watering.");
        } else {
            DiseaseImageView.setImageResource(R.drawable.healthy);
            DiseaseNameTextView.setText("Healthy (स्वस्थ)");
            DiseaseReasonTextView.setText("Your Leaf is Healthy");
            DiseaseCureTextView.setText("No cure needed");
            DiseasePreventionTextView.setText("Keep it up");
        }
    }
}