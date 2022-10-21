package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private static final String KEY_INDEX = "index";

    // Array con las preguntas
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Guardar index de la pregunta para cuando rotas
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

            // Cargar las respuesta del usuario en el array de preguntas
            String[] respUsuarios = savedInstanceState.getStringArray("array");
            for (int i=0;i < mQuestionBank.length;i++){
                mQuestionBank[i].setUserAnswer(respUsuarios[i]);
            }
        }

        // Mostrar pregunta en textview
        mQuestionTextView = (TextView) findViewById(R.id.text_question);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        // Boton TRUE
        mTrueButton = (Button) findViewById(R.id.button_true);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprobar respuesta y deshabilitar botones
                checkAnswer(true);
                mQuestionBank[mCurrentIndex].setUserAnswer("true");
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
            }
        });

        // Boton FALSE
        mFalseButton = (Button) findViewById(R.id.button_false);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprobar respuesta y deshabilitar botones
                checkAnswer(false);
                mQuestionBank[mCurrentIndex].setUserAnswer("false");
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
            }
        });

        // Boton NEXT
        mNextButton = (ImageButton) findViewById(R.id.button_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pasar a la siguiente pregunta y actualizar la pantalla
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
                updateQuestion();
            }
        });

        // Boton PREVIOUS
        mPreviousButton = (ImageButton) findViewById(R.id.button_previous);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pasar a la anterior pregunta y actualizar la pantalla
                // Si la posicion actual es 0 se pasa a la ultima pregunta del array
                if(mCurrentIndex == 0){
                    mCurrentIndex = mQuestionBank.length-1;
                }else{
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                }

                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
                updateQuestion();
            }
        });

        updateQuestion();

    }

    private void updateQuestion() {
        // Guardar pregunta actual
        int question = mQuestionBank[mCurrentIndex].getTextResId();

        // Hacer activos los botones
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
        // Poner el texto de las preguntas de color negro
        mQuestionTextView.setTextColor(getResources().getColor(R.color.black));

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        //Comprobar que el usuario haya contestado la pregunta
        if(mQuestionBank[mCurrentIndex].getUserAnswer() != ""){
            // Desactivar los botones
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
            boolean respuesta;

            // Guardar la respuesta del usuario en una variable
            if (mQuestionBank[mCurrentIndex].getUserAnswer() == "true"){
                respuesta = true;
            }else{
                respuesta = false;
            }

            // Comprobar que la respuesta del usuario era correcta
            if (answerIsTrue == respuesta) {
                //Cambiar de color el texto
                mQuestionTextView.setTextColor(getResources().getColor(R.color.green));
            } else {
                //Cambiar de color el texto
                mQuestionTextView.setTextColor(getResources().getColor(R.color.red));

            }
        }

        // Poner pregunta en pantalla
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        // Comprobar si la respuesta dada por el usuario es correcta
        if (userPressedTrue == answerIsTrue) {
            // Cambiar de color el texto y mostrar resultado
            messageResId = R.string.correct_toast;
            mQuestionTextView.setTextColor(getResources().getColor(R.color.green));
        } else {
            //  Cambiar de color el texto y mostrar resultado
            mQuestionTextView.setTextColor(getResources().getColor(R.color.red));
            messageResId = R.string.incorrect_toast;
        }
        // Mostrar toast con el resultado
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Guardar la pregunta cuando se gira la pantalla
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

        // Guardar respuestas de los usuarios para cuando giren la pantalla
        String[] respUsuarios = new String[mQuestionBank.length];
        for (int i=0;i < mQuestionBank.length;i++){
            respUsuarios[i] = mQuestionBank[i].getUserAnswer();
        }
        savedInstanceState.putStringArray("array", respUsuarios);
    }

}