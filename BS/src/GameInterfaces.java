interface DealerInterface
{
    public void DealCards(Deck dealingDeck, Player[] players);
}

interface PlayerInterface
{
    public void playerTurn();
    public boolean checkIfWon();
    public void cardDecision();
    public void drawCard();

}
