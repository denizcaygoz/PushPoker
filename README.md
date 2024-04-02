# PushPoker

A card game project for a university class. The game is very similar to usual poker but has some differences.  

![pushpokeringame](https://github.com/denizcaygoz/PushPoker/assets/46894986/af3cd436-959e-40a0-a032-4eac87240537)

## Rules 

* The object of the game is to have the best hand score among the other players.  
The combination of each player's 3 open and 2 hidden cards determines the value of each player's hand.
* The game is played between 2-4 players.
* The number of rounds can be set from 2 to 7.
* Each player has 2 hidden and 3 open cards, but unlike hidden cards, open cards can be seen by other players.
* There is 1 draw stack, 1 discard stack, and 3 cards in the middle.
* When it is the player's turn to move, the player must first perform the "Push Left" or "Push Right" action.
* Push Left discards the most-left card on the mid, shifts the other two cards to the left and places a new card from draw stack to the right most position on the mid.
* Push Right discards the most-right card on the mid, shifts the other two cards to the right and places a new card from draw stack to the left most position on the mid.
* After the push action is performed, the player may perform one of the following actions: swap one, swap all, end turn.  
After performing any of these actions, the player ends his turn.
* To swap one, player must select one of his cards and one of the cards on the middle to swap.
* To swap all, player click "Swap All" button and swaps all of his 3 open cards with 3 cards in the middle.
* To end a turn, player clicks "End Turn" button and ends his turn. The other player's turn begins.
* When all players have completed their turn, a round is over. When all rounds are complete,  
the game ends and players are ranked according to their Hand Value Score
* The hand value score is calculated using standard poker rules.


## Use Case Diagramm
![push poker use case diagramm](https://github.com/denizcaygoz/PushPoker/assets/46894986/140a9195-0ca7-4935-92c6-0fee28394f72)

## Class Diagramm
![11](https://github.com/denizcaygoz/PushPoker/assets/46894986/ed01586f-70f6-49e9-9ebe-5e0d848fc3ad)
![22](https://github.com/denizcaygoz/PushPoker/assets/46894986/92e20a84-9be7-4415-942e-a972f6a40e06)
![33](https://github.com/denizcaygoz/PushPoker/assets/46894986/50c36f6f-53bf-40e8-8973-1f9f03a78858)


## GUI

* For the GUI bgw framework is used in the project. https://github.com/tudo-aqua/bgw.
* The game is designed to play one a single monitor. When the current player finishes his turn, the other player comes in front of the monitor and clicks Start Turn.

![hastheturn](https://github.com/denizcaygoz/PushPoker/assets/46894986/3fd081e6-6fb6-4c94-ab7b-1830e856f1ca)

## Plans To Optimize The GUI For The Future Releases

* A good looking main menu and scoreboard menu.  
* A better background and buttons for the in-game scene.
* An AI can be integrated into the game.

## License

Distributed under the MIT License.



