package com.egoregorov.colourmemory.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.egoregorov.colourmemory.model.Board;
import com.egoregorov.colourmemory.model.Card;
import com.egoregorov.colourmemory.view.BoardView;

import java.util.concurrent.TimeUnit;

/**
 * Created by Egor on 04.12.2017.
 */

public class BoardPresenter implements Presenter {
    private static final String TAG = "BoardPresenter";

    private Board model;
    private BoardView mBoardView;
    private int mScore;
    private Card mPreviousCard;
    private Card mCurrentCard;

    public BoardPresenter(BoardView boardView) {
        mBoardView = boardView;
        model = new Board();
        mScore = 0;
    }

    @Override
    public void onCreate() {
        model = new Board();
        mBoardView.startNewGame();
        mScore = 0;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public Card onCardSelected(int position) {
        if (model.getCard(position).isSelected()) {
            Log.d(TAG, "onCardSelected: return null");
            return null;
        } else {
            Log.d(TAG, "onCardSelected: return card");
            Card selectedCard = model.getCard(position);
            if (mPreviousCard != null) {
                mCurrentCard = selectedCard;

                if (selectedCard.getImageResourceId() == mPreviousCard.getImageResourceId()) {
                    //TODO wait for 1 sec and dismiss cards, and add 2 points to the score
                    new DismissCardsTask().execute(true);

                } else {
                    //TODO wait for 1 sec and dismiss card, and remove 1 point from score
                    mCurrentCard.setSelected(false);
                    mPreviousCard.setSelected(false);
                    new DismissCardsTask().execute(false);

                }
            } else {
                selectedCard.setSelected(true);
                mPreviousCard = selectedCard;
            }
            return selectedCard;
        }
    }

    public void onHighScoresSelected() {

    }

    class DismissCardsTask extends AsyncTask<Boolean, Void, Void> {
        boolean mWasRight;

        @Override
        protected Void doInBackground(Boolean... booleans) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mWasRight = booleans[0];
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mWasRight) {
                mScore = +2;
                mBoardView.gotTheScore(model.getCardPosition(mPreviousCard), model.getCardPosition(mCurrentCard), mScore);
            } else {
                if (mScore != 0) {
                    mScore--;
                }
                mBoardView.lostScore(model.getCardPosition(mPreviousCard), model.getCardPosition(mCurrentCard), mScore);
            }
            mCurrentCard = null;
            mPreviousCard = null;
            Log.d(TAG, "onPostExecute: current score is " + mScore);
        }
    }
}
