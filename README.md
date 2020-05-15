# 21 Card Game

Here are the rules for playing the card game, “21”: A player tries to collect
cards whose total score is 21 or as close as possible. The player with the highest
score not exceeding 21 wins. (If the player’s score is higher than 21, the player
is “busted” and loses.) A card’s has a point value based on its count (e.g., a
four of clubs is valued 4), but face cards are valued 10, and we will say that an
ace is valued 11.
Initially, each player receives two cards from the dealer. Then, each player can
request additional cards, one at a time, from the dealer. After all the players
have received desired additional cards, the players reveal their hands.
Write an application that lets a computerized dealer, a human player, and a
computerized player play 21. The computerized player should be smart enough
to request additional cards as long as the player’s total score is 16 or less.

Revise the “21” card game as follows:
(a) The dealer is also a player; therefore, the dealer must deal herself a hand.
(b) An ace has a value of either 1 or 11, based on the discretion of the player
who holds the card.
(c) In some casinos, a dealer deals from two decks of cards. Alter the application to deal alternately from two decks.
(d) If a player’s first two cards have identical counts (e.g., two eights or two
queens), the player can “split” the cards into two distinct hands and continue with two hands.
(e) The game lets the players play multiple rounds. In particular, this means
that the deck of cards must be “reshuffled” with the cards not in the
players’ hands when the deck is emptied of cards in the middle of a round.
