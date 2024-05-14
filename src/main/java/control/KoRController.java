package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.AIData;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.PlayerData;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.HeroCardStack;
import model.container.card.MovementCardSpread;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;
import utils.Strings;

public class KoRController extends Controller {

    private final ConsoleController console;

    boolean firstPlayer;
    private ActionList playerActionList;
    private GameConfigurationModel gameConfigurationModel;

    public KoRController(Model model, View view, ConsoleController consoleController, GameConfigurationModel gameConfigurationModel) {
        super(model, view);
        this.gameConfigurationModel = gameConfigurationModel;
        this.console = consoleController;
        this.firstPlayer = true;
    }

    /**
     * Defines what to do within the single stage of the single party
     * It is pretty straight forward to write :
     */
    public void stageLoop() {
        final KoRStageModel gameStage = (KoRStageModel) model.getGameStage();

        update();
        while (!model.isEndStage()) {
            // TANT QUE LA PARTIE N'EST PAS BLOQUÉ, ON JOUE
            while (!gameStage.gameIsStuck()) {
                final PlayerData playerData = PlayerData.getCurrentPlayerData(model);

                playTurn(gameStage, playerData);

                if (!model.isEndStage()) update();
            }

            gameStage.computePartyResult();
        }
        endGame();
    }

    private void playTurn(KoRStageModel gameStage, PlayerData playerData) {
        final ActionPlayer actionPlayer;

        // RÉCUPÈRE LE NOUVEAU JOUEUR
        final Player p = model.getCurrentPlayer();
        // IF player is a computer
        if (p.getType() == Player.COMPUTER) {
            // THEN play the turn automatically
            AIData ai = gameConfigurationModel.getPlayerDataAIDataMap().get(playerData);
            ActionList actionList = new ActionList();
            if(ai==AIData.RANDOM) {
                // add all the moves the AI has to do to play the turn
                actionList.addAll(new KoRDeciderCamarade(model, this).decide());
            }
            actionPlayer = new ActionPlayer(model, this, actionList);
        } else {
            boolean ok = false;
            while (!ok) {
                System.out.print(p.getName() + " > ");
                // ANALYSE L'ENTRÉE DU JOUEUR HUMAIN

                final String line = console.getConsoleLine();
                // REGARDE SI LE JOUEUR ARRETE LE JEU (EXEMPLE : CTRL + D ou "stop")
                if (line == null || line.equals("stop")) {
                    model.stopStage();
                    return;
                }

                ok = actionAnalyse(gameStage, playerData, line);
                // SI L'ENTRÉE N'EST PAS VALIDE, ALORS BOUCLÉ UNE FOIS DE PLUS SUR UNE NOUVELLE ENTRÉE
                if (!ok) {
                    System.out.println("incorrect instruction. retry !");
                }
            }
            // RÉCUPÈRE LES ACTIONS DU JOUEURS POUR EN FAIRE UN ACTIONPLAYER
            actionPlayer = new ActionPlayer(model, this, playerActionList);
        }

        // JOUE LES ACTIONS RENSEIGNÉES
        actionPlayer.start();

        // SI LE PROCHAIN JOUEUR PEUT JOUER, ALORS DÉCLARER LA FIN DU TOUR POUR PASSER AU JOUEUR SUIVANT
        if (gameStage.playerCanPlay(playerData.getNextPlayerData())) endOfTurn();
    }

    @Override
    public void endOfTurn() {
        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        KoRStageModel stageModel = (KoRStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
    }

    // RENVOIE FAUX SI L'ACTION N'EST PAS VALIDE
    private boolean actionAnalyse(KoRStageModel gameStage, PlayerData playerData, String line) {
        if (line.isEmpty() || line.length() > 2) return false;

        final char action = line.charAt(0);
        final int length = line.length();

        final SimpleActionList simpleActionList = new SimpleActionList(model);
        if (action == 'P') {
            if (length != 1) return false;

            final ContainerElement container;
            if (playerData == PlayerData.PLAYER_BLUE) {
                container = gameStage.getBlueMovementCardsSpread();
            } else {
                container = gameStage.getRedMovementCardsSpread();
            }
            // RÉCUPÈRE LA PREMIERE CASE VIDE DU JOUEUR DANS CA GRILLE DE CARTE DE DÉPLACEMENT
            final Coord2D coord2D = ContainerElements.getEmptyPosition(container);

            if (coord2D == null) {
                System.out.println("Vous avez déjà plus de 5 cartes mouvement");
                return false;
            }

            playerActionList = simpleActionList.pickUpMovementCard(container, coord2D);
        } else if (action == 'D') {
            if (length != 2) return false;

            // SI L'INDEX DE L'ACTION RENVOIE -1 ALORS CE N'EST PAS UN NOMBRE QUI SUIT LA LETTRE 'D'
            // note : on vérifie si l'index fait -2 (et pas -1) car on fait -1 sur la variable pour avoir le vrai index
            //        par exemple l'index de la carte 3 est 2 (donc 3-1)
            final int indexCard = Strings.parseInt(line.substring(1)) - 1;
            if (indexCard == -1) return false;

            final MovementCardSpread movementCardSpread;
            if (playerData == PlayerData.PLAYER_BLUE) {
                movementCardSpread = gameStage.getBlueMovementCardsSpread();
            } else {
                movementCardSpread = gameStage.getRedMovementCardsSpread();
            }

            final PawnPot pawnPot = gameStage.getGeneralPot(playerData);
            if(pawnPot == null) {
                Logger.info("BUG, pot vide, fin de partie !");
                return false;
            }

            // SI N'A PLUS DE CARTE MOUVEMENT
            if (movementCardSpread.isEmpty()) {
                System.out.println("Vous n'avez pas de carte mouvement à jouer.\nPiocher une carte!");
                return false;
            }

            // SI LA CARTE MOUVEMENT SELECTIONNÉ N'EXISTE PAS
            if (movementCardSpread.isEmptyAt(indexCard, 0)) {
                System.out.println("Sélectionner une carte que vous possédez.");
                return false;
            }

            // RÉCUPÈRE LES ÉLÉMENTS ESSENTIELS
            final KoRBoard board = gameStage.getBoard();
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(indexCard, 0);
            final GameElement king = gameStage.getKingPawn();

            // RÉCUPÈRE LA POSITION DU PION DU ROI QU'ON ADDITIONNE AU VECTEUR DE LA CARTE DÉPLACEMENT
            final Coord2D pos = ContainerElements.getElementPosition(king, board)
                    .add(movementCard.getDirectionVector());

            final int col = (int) pos.getX();
            final int row = (int) pos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA CASE AVEC LA CARTE
            if (!board.canReachCell(row, col) || !board.isEmptyAt(row, col)) {
                System.out.println("Vous ne pouvez pas jouer cette carte!");
                Logger.info("Sortie de tableau");
                return false;
            }

            playerActionList = simpleActionList.useMovementCard(movementCard, pos, playerData);
        } else if (action == 'H') {
            if (length != 2) return false;

            // SI L'INDEX DE L'ACTION RENVOIE -1 ALORS CE N'EST PAS UN NOMBRE QUI SUIT LA LETTRE 'D'
            // note : on vérifie si l'index fait -2 (et pas -1) car on fait -1 sur la variable pour avoir le vrai index
            //        par exemple l'index de la carte 3 est 2 (donc 3-1)
            final int indexCard = Strings.parseInt(line.substring(1)) - 1;
            if (indexCard == -1) return false;

            final MovementCardSpread movementCardSpread;
            final HeroCardStack heroCardStack;
            if (playerData == PlayerData.PLAYER_BLUE) {
                movementCardSpread = gameStage.getBlueMovementCardsSpread();
                heroCardStack = gameStage.getBlueHeroCardStack();
            } else {
                movementCardSpread = gameStage.getRedMovementCardsSpread();
                heroCardStack = gameStage.getRedHeroCardStack();
            }

            if (heroCardStack.isEmpty()) {
                System.out.println("Vous n'avez plus de carte héro à jouer.!");
                return false;
            }

            // SI N'A PLUS DE CARTE MOUVEMENT
            if (movementCardSpread.isEmpty()) {
                System.out.println("Vous n'avez pas de carte mouvement à jouer.\nPiocher une carte!");
                return false;
            }

            // SI LA CARTE MOUVEMENT SELECTIONNÉ N'EXISTE PAS
            if (movementCardSpread.isEmptyAt(indexCard, 0)) {
                System.out.println("Sélectionner une carte que vous possédez.");
                return false;
            }

            // RÉCUPÈRE LES ÉLÉMENTS ESSENTIELS
            final KoRBoard board = gameStage.getBoard();
            final HeroCard heroCard = (HeroCard) heroCardStack.getElement(0, 0);
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(indexCard, 0);
            final GameElement king = gameStage.getKingPawn();

            // RÉCUPÈRE LA POSITION DU PION DU ROI QU'ON ADDITIONNE AU VECTEUR DE LA CARTE DÉPLACEMENT
            final Coord2D pos = ContainerElements.getElementPosition(king, board)
                    .add(movementCard.getDirectionVector());

            final int col = (int) pos.getX();
            final int row = (int) pos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA CASE AVEC LA CARTE
            if (!board.canReachCell(row, col) || board.isEmptyAt(row, col)) {
                System.out.println("Vous ne pouvez pas jouer cette carte!");
                Logger.info("Sortie de tableau");
                return false;
            }

            // RÉCUPÈRE LE PION À RETOURNER ET CHANGE SA COULEUR
            final Pawn pawn = (Pawn) board.getElement(row, col);
            if (pawn.getStatus().isOwnedBy(playerData)) {
                System.out.println("Vous ne pouvez pas jouer cette carte!");
                Logger.info("Pion du joueur courant");
                return false;
            }

            playerActionList = simpleActionList.useHeroCard(heroCard, movementCard, pawn, pos);
        } else {
            return false;
        }

        Logger.info(playerActionList.toString());
        return true;
    }

}