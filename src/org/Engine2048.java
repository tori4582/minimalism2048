/**
 * 2048 GAME ENGINE - BinxEngine
 * 
 * This engine is designed for game system that perform a 2048 game logic. This Engine 
 *   provides a minimalist game logic and manipulation for programmers who want to use
 *   to develop their own 2048 game application.
 * 
 * @author: Pham Quoc Hung - Steve Alan
 * @releaseDate: May 2020
 */

package org;

public class Engine2048 {
 
    private int board[][] = new int[4][4];
    
    public static final int BOARD_SIZE = 4;
    public static final int FIRST_LIMIT = 0;
    public static final int LAST_LIMIT = 3;
    
    private static final int BLANK_ELEMENT = 0;
    private static final int FIRST_ELEMENT = 2;
    private static final int WIN_ELEMENT = 2048;
    
    private static final int INVALID_STATE = -1;
    
    private boolean movedFlag  = false;
    private boolean passedFlag = false;
    
    private int addedX = -1;
    private int addedY = -1;
    
    public Engine2048() {
        
        //initialization for the board
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                this.board[i][j] = BLANK_ELEMENT;
            }
        }

        //create a first element
        this.addNewElement();
    }
    
    public void addNewElement() {
        do {
            addedX = Coordinator.getRandom();
            addedY = Coordinator.getRandom();       
        } while (get(addedX, addedY) != BLANK_ELEMENT);
        set(addedX, addedY, FIRST_ELEMENT);
        
        System.out.println("Generate a new element at (" + addedX + ", " + addedY + ")");
    }
    
    public void printBoard() { 
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                System.out.print(get(i, j) + "\t");
            }
            System.out.println();
        }
    }
    
    public void activeDemoBoard() {
        int counter = 0;
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                if (i == 0 && j == 0) 
                    set(i, j, BLANK_ELEMENT);
                set(i, j, (int)Math.pow(FIRST_ELEMENT, counter++));
            }
        }
    }
    
    private boolean isValid(int x, int y) {
        return (
            ((x >= FIRST_LIMIT) && (x < BOARD_SIZE)) &&
            ((y >= FIRST_LIMIT) && (y < BOARD_SIZE))
        );
    }
    
    public void set(int x, int y, int value) {
        if (isValid(x, y))
            this.board[x][y] = value;
    }
    
    public int get(int x, int y) {
        if (isValid(x, y)) 
            return this.board[x][y];
        else
            return INVALID_STATE;
    }
    
    private int getElement(int level) {
        return (int)Math.pow(2, level);
    }
    
    public int getAddedX() {
        return this.addedX;
    }
    
    public int getAddedY() {
        return this.addedY;
    }
     
    private boolean isElementMovable(int x, int y) {
        return ( 
                 ((get(x, y) == get(x - 1, y)) || (get(x - 1, y) == BLANK_ELEMENT))  ||
                 ((get(x, y) == get(x + 1, y)) || (get(x + 1, y) == BLANK_ELEMENT))  ||
                 ((get(x, y) == get(x, y - 1)) || (get(x, y - 1) == BLANK_ELEMENT))  ||
                 ((get(x, y) == get(x, y + 1)) || (get(x, y + 1) == BLANK_ELEMENT))
               ); 
    }
    
    //Move a element from A to B through 2 checks :
    //  - Elements duplications / conflict
    //  - Elements replacability
    // and after moving step, delete the old element
    
    private void stackedMoveLeft(int x, int y) { 
        if (!isValid(x, y) || (get(x, y) == BLANK_ELEMENT)) return;
        if ((get(x, y) == get(x, y - 1)) || (get(x, y - 1) == BLANK_ELEMENT)) {
            moveElement(x, y, x, y - 1);
            movedFlag = true;
            stackedMoveLeft(x, y - 1);
        }
    }
    
    private void stackedMoveRight(int x, int y) {
        if (!isValid(x, y) || (get(x, y) == BLANK_ELEMENT)) return;
        if ((get(x, y) == get(x, y + 1)) || (get(x, y + 1) == BLANK_ELEMENT)) {
            moveElement(x, y, x, y + 1);
            movedFlag = true;
            stackedMoveRight(x, y + 1);
        }
    }
    
    private void stackedMoveUp(int x, int y) {
        if (!isValid(x, y) || (get(x, y) == BLANK_ELEMENT)) return;
        if ((get(x, y) == get(x - 1, y)) || (get(x - 1, y) == BLANK_ELEMENT)) {
            moveElement(x, y, x - 1, y);
            movedFlag = true;
            stackedMoveUp(x - 1, y);
        }
    }
    
    private void stackedMoveDown(int x, int y) {
        if (!isValid(x, y) || (get(x, y) == BLANK_ELEMENT)) return;
        if ((get(x, y) == get(x + 1, y)) || (get(x + 1, y) == BLANK_ELEMENT)) {
            moveElement(x, y, x + 1, y);
            movedFlag = true;
            stackedMoveDown(x + 1, y);
        }
    }
    
    private void moveElement(int x1, int y1, int x2, int y2) {
        if (!isValid (x1, y1) || !isValid(x2, y2)) return;
        if (get(x1, y1) == BLANK_ELEMENT) return;
        
        if (get(x2, y2) == get(x1, y1)) {
            System.out.println("MERGED from (" + x1 + ", " + y1 + ") : " + get(x1, y1) +
                " -> (" + x2 + ", " + y2 + ") : " + get(x2, y2));
        
            set(x2, y2, get(x2, y2)*2);
            set(x1, y1, BLANK_ELEMENT);
        } else if (get(x2, y2) == BLANK_ELEMENT) {
            System.out.println("MOVED from (" + x1 + ", " + y1 + ") : " + get(x1, y1) +
                " -> (" + x2 + ", " + y2 + ") : " + get(x2, y2));
            
            set(x2, y2, get(x1, y1));
            set(x1, y1, BLANK_ELEMENT);
            moveElement(x1, y1, x2, y2);
        }
    } 
    
    private void move(int x, int y, boolean isVertical, boolean isForward) {
        if (!isVertical) {
            if (!isForward) {
                // MOVE: HORIZONTAL, BACKWARD (LEFT)
                stackedMoveLeft(x, y);
            } else {
                // MOVE: HORIZONTAL, FORWARD (RIGHT)
                stackedMoveRight(x, y);
            }
        } else {
            if (!isForward) {
                // MOVE: VERTICAL, BACKWARD (UP)
                stackedMoveUp(x, y);
            } else {
                // MOVE: VERTICAL, FORWARD (DOWN)
                stackedMoveDown(x, y);
            }
        }
    }
    
    
    //Provoke:
    
    public void swipeLeft() {
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                move(i, j, false, false);
            }
        }
        if (movedFlag) {
            addNewElement();
            movedFlag = false;
        }
    }
    
    public void swipeRight() {
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = LAST_LIMIT; j >= FIRST_LIMIT; j--) {
                move(i, j, false, true);
            }
        }
        if (movedFlag) {
            addNewElement();
            movedFlag = false;
        }
    }
    
    public void swipeUp() {
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                move(i,j, true, false);
            }
        }
        if (movedFlag) {
            addNewElement();
            movedFlag = false;
        }
    }
    
    public void swipeDown() {
        for (int i = LAST_LIMIT; i >= FIRST_LIMIT; i--) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                move(i,j, true, true);
            }
        }
        if (movedFlag) {
            addNewElement();
            movedFlag = false;
        }
    }
    
    public boolean isFullBoard() {
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                if (get(i,j) == BLANK_ELEMENT) return false;
            }
        }
        return true;
    }
    
    public int getMax() {
        int maxValue = FIRST_ELEMENT;
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                if (get(i, j) > maxValue)
                    maxValue = get(i, j);
            }
        }
        return maxValue;
    }
    
    public boolean getPassedFlag() {
        return passedFlag;
    }
    
    public void flipPassedFlag() {
        this.passedFlag = !this.passedFlag;
    }
    
    public boolean isMoveable() {
        
        if (!isFullBoard()) return true;
        
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                if (isElementMovable(i, j))
                    return true;
            }
        }
        
        System.out.println("The Board has no more valid move!");;
        return false;
    }
    
    /**
     * 
     * 
     * 
     * @return 
     */
    
    public int getTotalScore() {
        
        int sum = 0;
        
        for (int i = FIRST_LIMIT; i < BOARD_SIZE; i++) {
            for (int j = FIRST_LIMIT; j < BOARD_SIZE; j++) {
                sum += get(i, j);
            }
        }
            
        return sum;
    }
    
}
