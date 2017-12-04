package com.egoregorov.colourmemory.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.egoregorov.colourmemory.R;
import com.egoregorov.colourmemory.model.Card;
import com.egoregorov.colourmemory.presenter.BoardPresenter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements BoardView {
    private static final String TAG = "MainActivityFragment";

    private View mView;
    private GridView mBoard;
    private BoardPresenter mBoardPresenter = new BoardPresenter(this);
    private boolean mOneSelected = false;
    private TextView mScore;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        mBoard = mView.findViewById(R.id.board);
        mBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Card card = mBoardPresenter.onCardSelected(position);
                if (card != null) {
                    ImageView imageView = (ImageView) view;
                    imageView.setImageResource(card.getImageResourceId());
                    if (mOneSelected) {
                        mBoard.setEnabled(false);
                        mOneSelected = false;
                    } else {
                        mOneSelected = true;
                    }
                }
            }
        });
        mBoardPresenter.onCreate();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScore = getActivity().findViewById(R.id.score);
    }

    @Override
    public void startNewGame() {
        mBoard.setAdapter(new BoardAdapter(getActivity()));
    }

    @Override
    public void lostScore(int position1, int position2, int currentScore) {
        Log.d(TAG, "lostScore: position1 " + position1);
        Log.d(TAG, "lostScore: position2 " + position2);
        ImageView card;
        card = (ImageView) mBoard.getItemAtPosition(position1);
        card.setImageResource(R.drawable.card_bg);
        card = (ImageView) mBoard.getItemAtPosition(position2);
        card.setImageResource(R.drawable.card_bg);
        mBoard.setEnabled(true);
        Log.d(TAG, "lostScore: " + mScore.getText().toString());
        mScore.setText(String.valueOf(currentScore));

    }

    @Override
    public void gotTheScore(int position1, int position2, int currentScore) {
        ImageView card = (ImageView) mBoard.getItemAtPosition(position1);
        card.setVisibility(View.INVISIBLE);
        card.setClickable(false);
        card = (ImageView) mBoard.getItemAtPosition(position2);
        card.setVisibility(View.INVISIBLE);
        card.setClickable(false);
        mBoard.setEnabled(true);
        mScore.setText(String.valueOf(currentScore));
    }

    @Override
    public void gameCompleted() {
        Log.d(TAG, "gameCompleted: starts");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_congatulations, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("SKIP", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create().show();
    }
}
