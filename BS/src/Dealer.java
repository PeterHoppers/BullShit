public class Dealer implements DealerInterface
{
    int cardsPerPlayer;

    public Dealer()
    {
        cardsPerPlayer = 7;
    }

    public Dealer(int cards)
    {
        if (cards > 0)
            cardsPerPlayer = cards;
        else
            cardsPerPlayer = 1;
    }

    public void DealCards(Deck dealingDeck, Player[] players)
    {
        Card topCard;

        for (int currCards = 0; currCards < cardsPerPlayer; currCards++)
        {
            for(Player player : players)
            {
                topCard = dealingDeck.dealCard();
                player.hand.addCard(topCard);
            }
        }
    }
}