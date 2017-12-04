package com.egoregorov.colourmemory.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

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
                    if (mOneSelected){
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
    public void startNewGame() {
        mBoard.setAdapter(new BoardAdapter(getActivity()));
    }

    @Override
    public void lostScore(int position1, int position2) {
        Log.d(TAG, "lostScore: position1 " + position1);
        Log.d(TAG, "lostScore: position2 " + position2);
        ImageView card;
        card = (ImageView) mBoard.getItemAtPosition(position1);
        card.setImageResource(R.drawable.card_bg);
        card = (ImageView) mBoard.getItemAtPosition(position2);
        card.setImageResource(R.drawable.card_bg);
        mBoard.setEnabled(true);
    }

    @Override
    public void gotTheScore(int position1, int position2) {
        ImageView card = (ImageView) mBoard.getItemAtPosition(position1);
        card.setVisibility(View.INVISIBLE);
        card.setClickable(false);
        card = (ImageView) mBoard.getItemAtPosition(position2);
        card.setVisibility(View.INVISIBLE);
        card.setClickable(false);
        mBoard.setEnabled(true);

    }
}
