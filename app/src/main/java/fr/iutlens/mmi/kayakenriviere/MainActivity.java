package fr.iutlens.mmi.kayakenriviere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fr.iutlens.mmi.kayakenriviere.utils.OrientationProxy;

public class MainActivity extends AppCompatActivity {

    private OrientationProxy proxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère la vue du jeu
        GameView game = findViewById(R.id.gameView);
        TextView score = findViewById(R.id.textViewScore);
        game.setScoreView(score);

        // On configure le jeu pour recevoir les changements d'orientation
        proxy = new OrientationProxy(this,game);
    }


    @Override
    protected void onResume() {
        super.onResume();
        proxy.resume(); // On relance l'accéléromètre
    }

    @Override
    protected void onPause() {
        super.onPause();
        proxy.pause(); // On mets en pause l'accéléromètre
    }

}
