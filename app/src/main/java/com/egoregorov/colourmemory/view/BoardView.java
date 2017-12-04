package com.egoregorov.colourmemory.view;

/**
 * Created by Egor on 04.12.2017.
 */

public interface BoardView {
    void startNewGame();
    void lostScore(int position1, int position2);
    void gotTheScore(int position1, int position2);
}
