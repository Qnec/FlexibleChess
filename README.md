# Flexible Chess

This is a chess framework that reads game rule descriptions from files in a provided folder. It is intended to be capable of acting as an interface for all historical and non-historical chess variants that are actually playable (i.e. not infinite chess). This repository includes a full game description for modern chess, as well as a full game description for modern xiangqi (however it is missing proper icons for the pieces)

The project is compiled with `make.sh` and run with `run.sh [location of game description folder]`. For example to run the modern_chess description included in this project `./run.sh ./modern_chess` will work.

# Screenshots

## Chess
Game start

![Game state](images/modern_chess_start.png)

Piece selected for movement

![Piece selected for movement](images/modern_chess_selected_piece.png)

En Passant

![En Passant](images/modern_chess_en_passant.png)

Castling

![Castling](images/modern_chess_castling.png)

## Xiangqi

Game start

![Game start](images/modern_xiangqi_start.png)

Top Soldier moving to the sides after crossing the river, and bottom cannon having jumped over the top side's cannon

![Top Soldier moving to the sides after crossing the river, and bottom cannon having jumped over the top side's cannon](images/modern_xiangqi_soldier_river_and_cannon.png)

Top horse with blocked movement due to bottom cannon

![Top horse with blocked movement due to bottom cannon](images/modern_xiangqi_blocked_horse.png)
