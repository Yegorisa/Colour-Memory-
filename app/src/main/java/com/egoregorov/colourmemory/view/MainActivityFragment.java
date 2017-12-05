package com.egoregorov.colourmemory.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
public class MainActivityFragment extends Fragment implements BoardView{
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
        Log.d(TAG, "onCreateView: starts");

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
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScore = getActivity().findViewById(R.id.score);
        mBoardPresenter.onCreate();
    }

    @Override
    public void startNewGame() {
        mBoard.setAdapter(new BoardAdapter(getActivity()));
        mScore.setText("0");
    }

    @Override
    public void lostScore(int position1, int position2, int currentScore) {
        ImageView card;
        card = (ImageView) mBoard.getItemAtPosition(position1);
        card.setImageResource(R.drawable.card_bg);
        card = (ImageView) mBoard.getItemAtPosition(position2);
        card.setImageResource(R.drawable.card_bg);
        mBoard.setEnabled(true);
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
    public void gameCompleted(int finalScore) {
        DialogFragment dialogFragment = new CongratulationsDialogFragment();
        Bundle args = new Bundle();
        args.putInt("FINAL_SCORE", finalScore);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(),"CONGRATULATIONS_DIALOG");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("BOARD_PRESENTER",mBoardPresenter);
    }

    @Override
    public void onGameResumed() {

    }
}
