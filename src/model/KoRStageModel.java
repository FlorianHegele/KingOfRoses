package model;

import boardifier.model.*;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.MovementCardStack;
import model.container.card.HeroCardStack;
import model.container.card.MovementCardSpread;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;
import utils.Elements;

import java.util.*;

/**
 * KoRStageModel defines the model for the single stage in "The KoR". Indeed,
 * there are no levels in this game: a party starts and when it's done, the game is also done.
 *
 * KoRStageModel must define all that is needed to manage a party : state variables and game elements.
 * In the present case, there are only 2 state variables that represent the number of pawns to play by each player.
 * It is used to detect the end of the party.
 * For game elements, it depends on what is chosen as a final UI design. For that demo, there are 12 elements used
 * to represent the state : the main board, 2 pots, 8 pawns, and a text for current player.
 *
 * WARNING ! KoRStageModel DOES NOT create itself the game elements because it would prevent the possibility to mock
 * game element classes for unit testing purposes. This is why KoRStageModel just defines the game elements and the methods
 * to set this elements.
 * The instanciation of the elements is done by the KoRStageFactory, which uses the provided setters.
 *
 * KoRStageModel must also contain methods to check/modify the game state when given events occur. This is the role of
 * setupCallbacks() method that defines a callback function that must be called when a pawn is put in a container.
 * This is done by calling onPutInContainer() method, with the callback function as a parameter. After that call, boardifier
 * will be able to call the callback function automatically when a pawn is put in a container.
 * NB1: callback functions MUST BE defined with a lambda expression (i.e. an arrow function).
 * NB2:  there are other methods to defines callbacks for other events (see onXXX methods in GameStageModel)
 * In "The KoR", everytime a pawn is put in the main board, we have to check if the party is ended and in this case, who is the winner.
 * This is the role of computePartyResult(), which is called by the callback function if there is no more pawn to play.
 *
 */
public class KoRStageModel extends GameStageModel {

    private static final int[] dx = {1, -1, 0, 0};
    private static final int[] dy = {0, 0, 1, -1};

    // define stage game elements
    private KoRBoard board;

    private HeroCardStack blueHeroCardStack;
    private HeroCardStack redHeroCardStack;
    private HeroCard[] blueHeroCards;
    private HeroCard[] redHeroCards;

    private MovementCardStack movementCardStack;
    private MovementCardSpread blueMovementCardsSpread;
    private MovementCardSpread redMovementCardsSpread;
    private MovementCard[] movementCards;

    private PawnPot bluePot;
    private PawnPot redPot;
    private Pawn[] bluePawns;
    private Pawn[] redPawns;

    private Pawn kingPawn;

    // TEXT
    private TextElement playerName;

    private TextElement actionDescription1;
    private TextElement actionDescription2;
    private TextElement actionDescription3;
    private TextElement actionDescription4;

    private TextElement movementCardStackText;
    private TextElement bluePawnText;
    private TextElement redPawnText;
    private TextElement blueHeroCardText;
    private TextElement redHeroCardText;

    // Uncomment next line if the example with a main container is used. see end of KoRStageFactory and KoRStageView
    //private ContainerElement mainContainer;

    public KoRStageModel(String name, Model model) {
        super(name, model);

        setupCallbacks();
    }

    public KoRBoard getBoard() {
        return board;
    }
    public void setBoard(KoRBoard board) {
        this.board = board;
        addContainer(board);
    }

    public HeroCardStack getBlueHeroCardStack() {
        return blueHeroCardStack;
    }
    public void setBlueHeroCardStack(HeroCardStack blueHeroCardStack) {
        this.blueHeroCardStack = blueHeroCardStack;
        addContainer(blueHeroCardStack);
    }

    public HeroCardStack getRedHeroCardStack() {
        return redHeroCardStack;
    }
    public void setRedHeroCardStack(HeroCardStack redHeroCardStack) {
        this.redHeroCardStack = redHeroCardStack;
        addContainer(redHeroCardStack);
    }

    public HeroCard[] getBlueHeroCards() {
        return blueHeroCards;
    }
    public void setBlueHeroCards(HeroCard[] blueHeroCards) {
        this.blueHeroCards = blueHeroCards;
        for (HeroCard blueHeroCard : blueHeroCards) {
            addElement(blueHeroCard);
        }
    }

    public HeroCard[] getRedHeroCards() {
        return redHeroCards;
    }
    public void setRedHeroCards(HeroCard[] redHeroCards) {
        this.redHeroCards = redHeroCards;
        for (HeroCard redHeroCard : redHeroCards) {
            addElement(redHeroCard);
        }
    }

    public MovementCardStack getMovementCardStack() {
        return movementCardStack;
    }
    public void setMovementCardStack(MovementCardStack movementCardStack) {
        this.movementCardStack = movementCardStack;
        addElement(movementCardStack);
    }

    public List<MovementCard> getMovementCards(MovementCard.Owner owner) {
        final List<MovementCard> movementCardList = new ArrayList<>();
        for(MovementCard movementCard : movementCards) {
            if(movementCard.getOwner() == owner) movementCardList.add(movementCard);
        }
        return movementCardList;
    }
    public MovementCard[] getMovementCards() {
        return movementCards;
    }
    public void setMovementCards(MovementCard[] movementCards) {
        this.movementCards = movementCards;
        for (MovementCard movementCard : movementCards) {
            addElement(movementCard);
        }
    }

    public MovementCardSpread getBlueMovementCardsSpread() {
        return blueMovementCardsSpread;
    }
    public void setBlueMovementCardsSpread(MovementCardSpread blueMovementCardsSpread) {
        this.blueMovementCardsSpread = blueMovementCardsSpread;
        addContainer(blueMovementCardsSpread);
    }
    
    public MovementCardSpread getRedMovementCardsSpread() {
        return redMovementCardsSpread;
    }
    public void setRedMovementCardsSpread(MovementCardSpread redMovementCardsSpread) {
        this.redMovementCardsSpread = redMovementCardsSpread;
        addContainer(redMovementCardsSpread);
    }

    public PawnPot getBluePot() {
        return bluePot;
    }
    public void setBluePot(PawnPot bluePot) {
        this.bluePot = bluePot;
        addContainer(bluePot);
    }

    public PawnPot getRedPot() {
        return redPot;
    }
    public void setRedPot(PawnPot redPot) {
        this.redPot = redPot;
        addContainer(redPot);
    }
    
    public Pawn[] getBluePawns() {
        return bluePawns;
    }
    public void setBluePawns(Pawn[] bluePawns) {
        this.bluePawns = bluePawns;
        for (Pawn bluePawn : bluePawns) {
            addElement(bluePawn);
        }
    }

    public Pawn[] getRedPawns() {
        return redPawns;
    }
    public void setRedPawns(Pawn[] redPawns) {
        this.redPawns = redPawns;
        for (Pawn redPawn : redPawns) {
            addElement(redPawn);
        }
    }

    public Pawn getKingPawn() {
        return kingPawn;
    }
    public void setKingPawn(Pawn kingPawn) {
        this.kingPawn = kingPawn;
        addElement(kingPawn);
    }

    public TextElement getPlayerName() {
        return playerName;
    }
    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    public TextElement getActionDescription1() {
        return actionDescription1;
    }
    public void setActionDescription1(TextElement actionDescription1) {
        this.actionDescription1 = actionDescription1;
        addElement(actionDescription1);
    }

    public TextElement getActionDescription2() {
        return actionDescription2;
    }
    public void setActionDescription2(TextElement actionDescription2) {
        this.actionDescription2 = actionDescription2;
        addElement(actionDescription2);
    }

    public TextElement getActionDescription3() {
        return actionDescription3;
    }
    public void setActionDescription3(TextElement actionDescription3) {
        this.actionDescription3 = actionDescription3;
        addElement(actionDescription3);
    }

    public TextElement getActionDescription4() {
        return actionDescription4;
    }
    public void setActionDescription4(TextElement actionDescription4) {
        this.actionDescription4 = actionDescription4;
        addElement(actionDescription4);
    }

    public TextElement getMovementCardStackText() {
        return movementCardStackText;
    }
    public void setMovementCardStackText(TextElement movementCardStackText) {
        this.movementCardStackText = movementCardStackText;
        addElement(movementCardStackText);
    }

    public TextElement getBluePawnText() {
        return bluePawnText;
    }
    public void setBluePawnText(TextElement bluePawnText) {
        this.bluePawnText = bluePawnText;
        addElement(bluePawnText);
    }

    public TextElement getRedPawnText() {
        return redPawnText;
    }
    public void setRedPawnText(TextElement redPawnText) {
        this.redPawnText = redPawnText;
        addElement(redPawnText);
    }

    public TextElement getBlueHeroCardText() {
        return blueHeroCardText;
    }
    public void setBlueHeroCardText(TextElement blueHeroCardText) {
        this.blueHeroCardText = blueHeroCardText;
        addElement(blueHeroCardText);
    }

    public TextElement getRedHeroCardText() {
        return redHeroCardText;
    }
    public void setRedHeroCardText(TextElement redHeroCardText) {
        this.redHeroCardText = redHeroCardText;
        addElement(redHeroCardText);
    }


    // READ EVENT
    private void setupCallbacks() {
        onRemoveFromContainer((element, containerFrom, rowDest, colDest) -> {
            // ACTION : Joue une carte déplacement
            if(element instanceof MovementCard movementCard) {
                // CHANGE LE STATUS DE LA CARTE DÉPLACEMENT
                movementCard.setOwner(MovementCard.Owner.OUT);
                return;
            }

            // ACTION : Joue une carte héro
            if(element instanceof HeroCard heroCard) {
                // MET À JOUR LE COMPTEUR DE CARTE HÉRO
                final TextElement textElement;
                if(heroCard.getStatus() == HeroCard.Status.BLUE_CARD) {
                    textElement = blueHeroCardText;
                } else {
                    textElement = redHeroCardText;
                }
                Elements.updateText(textElement, ContainerElements.countElements(containerFrom));
            }
        });

        onPutInContainer((element, containerDest, rowDest, colDest) -> {
            // ACTION : Prendre une carte mouvement de la pile
            if(containerDest instanceof MovementCardSpread) {
                // CHANGE LE POSSESSEUR DE LA CARTE
                final MovementCard.Owner owner = (containerDest == blueMovementCardsSpread)
                        ? MovementCard.Owner.PLAYER_BLUE : MovementCard.Owner.PLAYER_RED;
                ((MovementCard)element).setOwner(owner);

                // SI IL N'Y A PLUS DE CARTE DANS LA PILE ALORS LA REFAIRE
                if(getMovementCards(MovementCard.Owner.STACK).isEmpty()) redoMovementCardStack();

                // MET À JOUR LE COMPTEUR DE LA PILE
                movementCardStackText.setText(String.valueOf(getMovementCards(MovementCard.Owner.STACK).size()));
            }

            // ACTION : Placer un pion sur le plateau
            else if(containerDest == board) {
                Pawn pawn = (Pawn) element;

                // RÉCUPÈRE LES ÉLÉMENTS POUR METTRE À JOUR LE COMPTEUR DES PIONS DU JOUEUR
                final TextElement textElement;
                final PawnPot pawnPot;
                if(pawn.getStatus() == Pawn.Status.BLUE_PAWN) {
                    textElement = bluePawnText;
                    pawnPot = bluePot;
                } else if (pawn.getStatus() == Pawn.Status.RED_PAWN) {
                    textElement = redPawnText;
                    pawnPot = redPot;
                } else {
                    // SI LE PION PLACÉ EST LE ROI ALORS NE RIEN FAIRE (joué 1 fois lors du setup)
                    return;
                }

                // MET À JOUR L'AFFICHAGE DU COMPTEUR DES PIONS
                textElement.setText(String.valueOf(ContainerElements.countElements(pawnPot)));
            }

            // REGARDE SI LA PARTIE PREND FIN
            if(gameIsStuck()) {
                computePartyResult();
            }
        });
    }

    public List<String> getPossiblePlayerActions(PlayerData playerData) {
        final List<String> actions = new ArrayList<>();
        if(playerData == null) return actions;

        final PawnPot pawnPot;
        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        if(playerData == PlayerData.PLAYER_BLUE) {
            pawnPot = bluePot;
            movementCardSpread = blueMovementCardsSpread;
            heroCardStack = blueHeroCardStack;
        } else {
            pawnPot = redPot;
            movementCardSpread = redMovementCardsSpread;
            heroCardStack = redHeroCardStack;
        }

        // SI LE JOUEUR N'A PLUS DE PION, ALORS IL NE PEUT RIEN FAIRE
        if(pawnPot.isEmpty()) return actions;

        // SI LE JOUEUR PEUT PIOCHER UNE CARTE DE MOUVEMENT
        final int countMovementCards = ContainerElements.countElements(movementCardSpread);
        if(countMovementCards < 5) {
            // RAJOUTER L'ACTION DE PIOCHER
            actions.add("P");
        }

        // SI LE JOUEUR NE POSSÈDE PAS DE CARTE MOUVEMENT ALORS RENVOYER L'ACTION DE PIOCHER UNIQUEMENT
        if(countMovementCards == 0) return actions;

        final boolean hasHeroCard = ContainerElements.countElements(heroCardStack) > 0;
        final Coord2D kingPos = ContainerElements.getElementPosition(kingPawn, board);
        final int cardCol = 0;
        for(int cardRow = 0; cardRow < countMovementCards; cardRow++) {
            if(movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // RÉCUPÈRE CHAQUE CARTE DIRECTION DU JOUEUR
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);
            // RÉCUPÈRE L'EMPLACEMENT POTENTIEL DU ROI AVEC LA CARTE DIRECTION JOUÉE
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA POSITION POTENTIEL, ALORS ON PASSE À LA CARTE SUIVANTE
            if(!board.canReachCell(row, col)) continue;

            // SI L'EMPLACEMENT EST VIDE, ALORS RAJOUTER L'ACTION DU DÉPLACEMENT SIMPLE
            // SINON SI LE JOUEUR POSSÈDE AU MOINS UNE CARTE HERO ET QUE LE PION N'EST PAS
            // LE SIEN ALORS RAJOUTER L'ACTION DE LA CARTE DÉPLACEMENT + HÉRO
            if(board.isEmptyAt(row, col)) {
                actions.add("D"+(cardRow+1));
            } else if(hasHeroCard && !((Pawn)board.getElement(row, col)).getStatus().isOwnedBy(playerData)) {
                actions.add("H"+(cardRow+1));
            }
        }
        return actions;
    }

    public boolean playerCanPlay(PlayerData playerData) {
        final PawnPot pawnPot;
        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        if(playerData == PlayerData.PLAYER_BLUE) {
            pawnPot = bluePot;
            movementCardSpread = blueMovementCardsSpread;
            heroCardStack = blueHeroCardStack;
        } else {
            pawnPot = redPot;
            movementCardSpread = redMovementCardsSpread;
            heroCardStack = redHeroCardStack;
        }

        // SI LE JOUEUR N'A PLUS DE PION, ALORS IL NE PEUT RIEN FAIRE
        if(pawnPot.isEmpty()) return false;

        // SI LE JOUEUR PEUT PIOCHER UNE CARTE DE MOUVEMENT
        final int countMovementCards = ContainerElements.countElements(movementCardSpread);
        if(countMovementCards < 5) return true;

        final boolean hasHeroCard = ContainerElements.countElements(heroCardStack) > 0;
        final Coord2D kingPos = ContainerElements.getElementPosition(kingPawn, board);
        final int cardCol = 0;
        for(int cardRow = 0; cardRow < countMovementCards; cardRow++) {
            if(movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // RÉCUPÈRE CHAQUE CARTE DIRECTION DU JOUEUR
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);
            // RÉCUPÈRE L'EMPLACEMENT POTENTIEL DU ROI AVEC LA CARTE DIRECTION JOUÉE
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA POSITION POTENTIEL, ALORS ON PASSE À LA CARTE SUIVANTE
            if(!board.canReachCell(row, col)) continue;

            // RENVOIE VRAI SI LE JOUEUR PEUT JOUER UNE CARTE MOUVEMENT
            // OU SI IL PEUT LA JOUER AVEC UNE CARTE HÉRO
            if(board.isEmptyAt(row, col) || (hasHeroCard && !((Pawn)board.getElement(row, col)).getStatus().isOwnedBy(playerData))) {
                return true;
            }
        }
        return false;
    }

    public boolean gameIsStuck() {
        return !playerCanPlay(PlayerData.PLAYER_BLUE) && !playerCanPlay(PlayerData.PLAYER_RED);
    }


    private void computePartyResult() {
        final int idWinner;
        final int redZoneCounter = getPlayerZonePoint(Pawn.Status.RED_PAWN);
        final int blueZoneCounter = getPlayerZonePoint(Pawn.Status.BLUE_PAWN);

        board.resetReachableCells(true);

        if(redZoneCounter == blueZoneCounter) {
            final int redPawnPlaced = getTotalPawnOnBoard(Pawn.Status.RED_PAWN);
            final int bluePawnPlaced = getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN);

            if(redPawnPlaced == bluePawnPlaced) {
                // ÉGALITÉ PARFAITE
                idWinner = -1;
            } else {
                // RÉCUPÈRE LE JOUEUR QUI A LE PLUS DE PION DE SA COULEUR VISIBLE POUR LE METTRE GAGNANT
                final PlayerData winner = (redPawnPlaced > bluePawnPlaced) ? PlayerData.PLAYER_RED : PlayerData.PLAYER_BLUE;
                idWinner = winner.getId();
            }
        } else {
            // RÉCUPÈRE LE JOUEUR QUI A LE PLUS DE POINT DE ZONE POUR LE METTRE GAGNANT
            final PlayerData winner = (redZoneCounter > blueZoneCounter) ? PlayerData.PLAYER_RED : PlayerData.PLAYER_BLUE;
            idWinner = winner.getId();
        }

        // set the winner
        model.setIdWinner(idWinner);
        // stop de the game
        model.stopStage();
    }

    private int getPlayerZonePoint(Pawn.Status status) {
        final Deque<PawnNode> pawnNodes = new LinkedList<>();

        int totalCounter = 0;
        for(int row=0; row < board.getNbRows(); row++) {
            for(int col=0; col < board.getNbCols(); col++) {
                final Pawn pawn = getPlayedPawn(row, col);

                // SI IL N'Y A PAS DE PION, ALORS RENDRE LA CASE INATTEIGNABLE ET PASSER À LA CASSE SUIVANTE
                if(pawn == null) {
                    board.setCellReachable(row, col, false);
                    continue;
                }

                // SI ON NE PEUT PAS ATTEINDRE LA CELLULE OU QUE LE PION EST CELUI DE L'ADVERSAIRE,
                // ALORS PASSER À LA CELLULE SUIVANTE
                if(!board.canReachCell(row, col) || pawn.getStatus() != status) continue;


                // AJOUTE UNE REFERENCE DU PION DANS UNE LISTE ET INITIALISE LE COMPTEUR DE VOISIN
                pawnNodes.add(new PawnNode(status, row, col));
                int counter = 0;

                while(!pawnNodes.isEmpty()) {
                    // TANT QUE LA LISTE N'EST PAS VIDE ON Y RAJOUTER LES RÉFÉRENCES DES PIONS VOISINS ATTEIGNABLES
                    // DE MEME COULER PAR RAPPORT AU 1ER RÉFÉRENTIEL DE LA LISTE TOUT EN L'ENLEVANT DE LA LISTE
                    // + ON INCRÉMENTE DE 1 LE COMPTEUR DE VOISIN

                    counter++;
                    pawnNodes.addAll(getNeighbors(pawnNodes.peek()));
                }

                // AJOUTE AU COMPTEUR FINAL LE COMPTEUR DE VOISIN AU CARRÉ
                totalCounter += counter * counter;
            }
        }

        return totalCounter;
    }

    private List<PawnNode> getNeighbors(PawnNode pawnNode) {
        final List<PawnNode> neighbors = new ArrayList<>();

        for(int i=0; i<4; i++) {
            final int row = dy[i] + pawnNode.row;
            final int col = dx[i] + pawnNode.col;

            // SI ON NE PEUT PAS ATTEINDRE LA CELLULE PROCHAINE OU QU'ELLE A DÉJÀ ÉTÉ ATTEINTE
            // ALORS NE RIEN FAIRE ET PASSER À LA CELLULE SUIVANTE
            if(!board.canReachCell(row, col)) continue;

            final Pawn pawn = getPlayedPawn(row, col);

            // SI LA CASSE EST VIDE (OU QUE LES COORDONNÉES SONT HORS DU TABLEAU), ALORS RENDRE
            // LA CELLULE INATTEIGNABLE (SI ON EST TOUJOURS DANS LE TABLEAU) ET PASSER À LA CASE VOISINE SUIVANTE
            if(pawn == null) {
                board.setCellReachable(row, col, false);
                continue;
            }

            // SI LE PION EST CELUI DE L'ADVERSAIRE ALORS PASSER À LA CASE VOISINE SUIVANTE
            if(pawn.getStatus() != pawnNode.status) continue;

            // AJOUTE LE PION À LA LISTE ET RENDRE LA CELLULE INATTEIGNABLE
            neighbors.add(new PawnNode(pawnNode.status, col, row));
            board.setCellReachable(row, col, false);
        }

        return neighbors;
    }

    private int getTotalPawnOnBoard(Pawn.Status status) {
        int totalPawn = 0;

        for (int row = 0; row < board.getNbRows(); row++) {
            for (int col = 0; col < board.getNbCols(); col++) {
                final Pawn pawn = getPlayedPawn(row, col);

                // SI IL N'Y A PAS DE PION, ALORS PASSER À LA CASSE SUIVANTE
                if (pawn == null) continue;

                // INCRÉMENTER LE COMPTEUR SI LE PION EST BIEN DE LA BONNE COULEUR
                if(pawn.getStatus() == status) totalPawn++;
            }
        }

        return totalPawn;
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new KoRStageFactory(this);
    }

    private void redoMovementCardStack() {
        // RÉCUPÈRE LES CARTES MOVEMENT DÉJÀ JOUÉES
        List<MovementCard> movementCardList = getMovementCards(MovementCard.Owner.OUT);

        // MÉLANGER LES CARTES QUI ONT ÉTÉ JOUÉES
        Collections.shuffle(movementCardList);

        // REMET LES CARTES JOUÉES DANS LA PILE
        for(MovementCard movementCard : movementCardList) {
            movementCard.setOwner(MovementCard.Owner.STACK);
            movementCardStack.addElement(movementCard, 0, 0);
        }
    }

    public Pawn getPlayedPawn(int row, int col) {
        for(GameElement gameElement : board.getElements(row, col)) {
            final Pawn pawn = (Pawn) gameElement;
            if(pawn == kingPawn) continue;
            return pawn;
        }
        return null;
    }

    private record PawnNode(Pawn.Status status, int row, int col) {}
}
