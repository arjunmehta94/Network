CS61Bnetwork
============

The game of Network

Network is played on an
8-by-8 board.  There are two players, "Black" and "White."  Each player has ten
chips of its own color to place on the board.  White moves first.

                 -----------------------------------------
                 |    | 10 | 20 | 30 | 40 | 50 | 60 |    |
                 -----------------------------------------
                 | 01 | 11 | 21 | 31 | 41 | 51 | 61 | 71 |
                 -----------------------------------------
                 | 02 | 12 | 22 | 32 | 42 | 52 | 62 | 72 |
                 -----------------------------------------
                 | 03 | 13 | 23 | 33 | 43 | 53 | 63 | 73 |
                 -----------------------------------------
                 | 04 | 14 | 24 | 34 | 44 | 54 | 64 | 74 |
                 -----------------------------------------
                 | 05 | 15 | 25 | 35 | 45 | 55 | 65 | 75 |
                 -----------------------------------------
                 | 06 | 16 | 26 | 36 | 46 | 56 | 66 | 76 |
                 -----------------------------------------
                 |    | 17 | 27 | 37 | 47 | 57 | 67 |    |
                 -----------------------------------------

The board has four goal areas:  the top row, the bottom row, the left column,
and the right column.  Black's goal areas are squares 10, 20, 30, 40, 50, 60
and 17, 27, 37, 47, 57, 67.  Only Black may place chips in these areas.
White's goal areas are 01, 02, 03, 04, 05, 06 and 71, 72, 73, 74, 75, 76; only
White may play there.  The corner squares--00, 70, 07, and 77--are dead;
neither player may use them.  Either player may place a chip in any square not
on the board's border.

Each player tries to complete a "network" joining its two goal areas.
A network is a sequence of six or more chips that starts in one of the player's
goal areas and terminates in the other.  Each consecutive pair of chips in the
sequence are connected to each other along straight lines, either orthogonally
(left, right, up, down) or diagonally.


For full spec description, see http://www.cs.berkeley.edu/~jrs/61b/hw/pj2/readme
