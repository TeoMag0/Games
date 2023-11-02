public class Hand {
    public static DLList<Card> hand = new DLList<Card>();
    public static DLList<Card> copy = new DLList<Card>();

    public static Card[][] replaceHand(boolean[] cardsToKeep){
        Card[][] newAndOldCards = new Card[2][];
        //get number of cards to remove
        int numToRemove = 0;
        for(boolean each : cardsToKeep){
            if(!each) numToRemove++;
        }
        newAndOldCards[0] = new Card[numToRemove];

        //remove cards from hand
        int returnIndex = 0;
        for(int i=hand.size()-1;i>=0;i--){
            if(!cardsToKeep[i]){
                Screen.deck.add(hand.get(i));
                newAndOldCards[0][returnIndex] = hand.get(i);
                hand.remove(i);
                returnIndex++;
            }
        }

        //add new cards to hand
        int cardsNeeded = 5-hand.size();
        newAndOldCards[1] = new Card[cardsNeeded];
        for(int i=0;i<cardsNeeded;i++){
            newAndOldCards[1][i] = Screen.deck.get(0);
            hand.add(Screen.deck.get(0));
            Screen.deck.remove(0);
        }
        return newAndOldCards;
    }
    public static int value(){
        //DLList<Card> 
        copy = hand.clone();
        Screen.sortList(copy);

        //set up checking vars
        int[] cardCounts = new int[13];
        boolean straight = true;
        boolean flush = true;
        DLList<Integer> pair = new DLList<Integer>();
        int three = 0;
        int four = 0;

        for(int i=0;i<copy.size();i++){
            //get pairs, 3, or 4
            cardCounts[copy.get(i).number()-2]++;
        }
        //checks pairs, 3, or 4
        for(int i=0;i<cardCounts.length;i++){
            switch(cardCounts[i]){
                case 2: pair.add(i+2); break;
                case 3: three = i+2; break;
                case 4: four = i+2; break;
            }
        }

        for(int i=1;i<copy.size();i++){
            //check for straight
            if(copy.get(i).number() != copy.get(i-1).number() + 1){
                //make sure it isn't A2345
                if(!straight || copy.get(i).number() != 14 || i != copy.size() - 1 || copy.get(i-1).number() != 5)
                    straight = false;
            }

            //check for flush
            if(copy.get(i).suit() != copy.get(i-1).suit())
                flush = false;
        }


        //get winnings:
        //royal flush
        if(flush && straight && copy.get(0).number() == 10){
            return 250;
        }
        //straight flush
        else if(flush && straight){
            return 50;
        }
        //four of a kind
        else if(four > 0){
            return 25;
        }
        //full house
        else if(three > 0 && pair.size() > 0){
            return 9;
        }
        //flush
        else if(flush){
            return 6;
        }
        //straight
        else if(straight){
            return 4;
        }
        //three of a kind
        else if(three > 0){
            return 3;
        }
        //2 pairs
        else if(pair.size() == 2){
            return 2;
        }
        //pair of faces
        else if(pair.size() == 1 && pair.get(0) > 10){
            return 1;
        }
        //anything else
        else{
            return 0;
        }
    }
}
