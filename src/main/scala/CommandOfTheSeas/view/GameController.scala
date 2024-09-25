package CommandOfTheSeas.view

import CommandOfTheSeas.model._
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.GridPane
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml
import CommandOfTheSeas.Main
import scalafx.scene.media.AudioClip


@sfxml
class GameController(
                      private val gridPane: GridPane,
                      private val detailLabel: Text,
                      private val moveButton: Button,
                      private val attackButton: Button,
                      private val actionLabel: Text,
                      private val turnText: Text,
                      private val actionText: Text,
                      private val healthText: Text,
                      private val rangeText: Text,
                      private val damageText: Text,
                      private val speedText: Text,
                      private val positionText: Text,
                      private val shipText: Text) {
  println("GameController running")
  detailLabel.text = "Battle start!"
  private var currentTeam = 1
  private var action = 0
  actionText.text = s"Actions left : ${2 - action}"

  private val pieces = initializePieces()
  private var selectedPiece: Option[GridNode] = None
  private var moveMode: Boolean = false
  private var attackMode: Boolean = false

  private val boom: AudioClip = createAudioClip("/CommandOfTheSeas/sound/boom.mp3", 0.2)
  private val move: AudioClip = createAudioClip("/CommandOfTheSeas/sound/Move.mp3", 0.2)
  private val click: AudioClip = createAudioClip("/CommandOfTheSeas/sound/Click.mp3", 0.2)
  private val error: AudioClip = createAudioClip("/CommandOfTheSeas/sound/error.mp3", 0.2)

  initializeGrid()


  //Define the pieces and their position
  private def initializePieces(): Seq[GridNode] = {
    Seq(
      new Destroyer(0, 0, 1), new Cruiser(1, 0, 1), new Battleship(2, 0, 1), new Carrier(3, 0, 1), new Carrier(4, 0, 1), new Battleship(5, 0, 1), new Cruiser(6, 0, 1), new Destroyer(7, 0, 1),
      new WaterTile(0, 1), new WaterTile(1, 1), new PatrolBoat(2, 1, 1), new PatrolBoat(3, 1, 1), new PatrolBoat(4, 1, 1), new PatrolBoat(5, 1, 1), new WaterTile(6, 1), new WaterTile(7, 1),
      new WaterTile(0, 2), new WaterTile(1, 2), new WaterTile(2, 2), new WaterTile(3, 2), new WaterTile(4, 2), new WaterTile(5, 2), new WaterTile(6, 2), new WaterTile(7, 2),
      new WaterTile(0, 3), new WaterTile(1, 3), new WaterTile(2, 3), new WaterTile(3, 3), new WaterTile(4, 3), new WaterTile(5, 3), new WaterTile(6, 3), new WaterTile(7, 3),
      new WaterTile(0, 4), new WaterTile(1, 4), new WaterTile(2, 4), new WaterTile(3, 4), new WaterTile(4, 4), new WaterTile(5, 4), new WaterTile(6, 4), new WaterTile(7, 4),
      new WaterTile(0, 5), new WaterTile(1, 5), new WaterTile(2, 5), new WaterTile(3, 5), new WaterTile(4, 5), new WaterTile(5, 5), new WaterTile(6, 5), new WaterTile(7, 5),
      new WaterTile(0, 6), new WaterTile(1, 6), new PatrolBoat(2, 6, 2), new PatrolBoat(3, 6, 2), new PatrolBoat(4, 6, 2), new PatrolBoat(5, 6, 2), new WaterTile(6, 6), new WaterTile(7, 6),
      new Destroyer(0, 7, 2), new Cruiser(1, 7, 2), new Battleship(2, 7, 2), new Carrier(3, 7, 2), new Carrier(4, 7, 2), new Battleship(5, 7, 2), new Cruiser(6, 7, 2), new Destroyer(7, 7, 2)
    )
  }

  //Create audio clips
  private def createAudioClip(path: String, volume: Double): AudioClip = {
    val clip = new AudioClip(getClass.getResource(path).toString)
    clip.volume = volume
    clip
  }

  //Initialize the grid
  private def initializeGrid(): Unit = {
    gridPane.children.clear()
    pieces.foreach { piece =>
      placeCharacter(piece.xPos, piece.yPos, piece)
    }
  }

  //Place the pieces on the grid
  private def placeCharacter(x: Int, y: Int, piece: GridNode): Unit = {
    val imageView = createImageView(piece)
    imageView.setOnMouseClicked { _ => handleGridClick(x, y, piece) }
    gridPane.add(imageView, x, y)
  }

  //Create image view
  private def createImageView(piece: GridNode): ImageView = {
    val characterImage = new Image(getClass.getResourceAsStream(piece.imagePath))
    new ImageView(characterImage) {
      fitWidth = 85
      fitHeight = 90
    }
  }

  //Handle grid click
  private def handleGridClick(x: Int, y: Int, piece: GridNode): Unit = {
    if (piece.isPiece) {
      if (!moveMode && !attackMode) {
        selectPiece(piece)
      } else if (attackMode && selectedPiece.isDefined) {
        attemptAttack(x, y, piece)
      } else {
        detailLabel.text = "Click on a piece first to select it"
      }
    } else if (moveMode && selectedPiece.isDefined) {
      attemptMove(x, y)
    } else {
      click.play()
      updateDetailLabel(piece)
    }
  }

  //Select a piece
  private def selectPiece(piece: GridNode): Unit = {
    selectedPiece = Some(piece)
    click.play()
    updateDetailLabel(piece)
  }


  //Update the detail label
  private def updateDetailLabel(piece: GridNode): Unit = {
    shipText.text = if (piece.isPiece) s"Ship Type: Player ${piece.team}'s ${piece.name}" else "Empty tile"
    healthText.text = s"Health: ${piece.health}"
    rangeText.text = s"Range: ${piece.range}"
    damageText.text = s"Damage: ${piece.damage}"
    speedText.text = s"Speed: ${piece.speed}"
    positionText.text = s"Current Position: (${piece.xPos}, ${piece.yPos})"
  }

  //Attempt to move a piece
  private def attemptMove(x: Int, y: Int): Unit = {
    val selected = selectedPiece.get
    val (oldX, oldY) = (selected.xPos, selected.yPos)
    val distance = calculateDistance(x, y, oldX, oldY)

    if (distance <= selected.speed) {
      movePiece(x, y, selected, oldX, oldY)
    } else {
      detailLabel.text = s"Move too far! Piece speed is ${selected.speed}."
      error.play()
    }
  }

  //Calculate distance
  private def calculateDistance(x1: Int, y1: Int, x2: Int, y2: Int): Int = {
    math.abs(x1 - x2) + math.abs(y1 - y2)
  }

  //Move a piece
  private def movePiece(x: Int, y: Int, selected: GridNode, oldX: Int, oldY: Int): Unit = {
    val emptyNode = pieces.find(p => p.xPos == x && p.yPos == y).get

    if (!emptyNode.isPiece) {
      selected.updatePosition(x, y)
      emptyNode.updatePosition(oldX, oldY)
      action += 1
      move.play()
      updateGrid()
      moveMode = false
      detailLabel.text = s"Piece moved from ($oldX, $oldY) to ($x, $y)."
    } else {
      detailLabel.text = "Cannot move to a position occupied by another piece"
      error.play()
    }
  }

  //Attempt to attack a piece
  private def attemptAttack(x: Int, y: Int, target: GridNode): Unit = {
    val attacker = selectedPiece.get
    val distance = calculateDistance(x, y, attacker.xPos, attacker.yPos)

    if (target.team == attacker.team) {
      error.play()
      detailLabel.text = "Cannot attack a piece on the same team!"
    } else if (distance <= attacker.range) {
      executeAttack(target, x, y)
    } else {
      error.play()
      detailLabel.text = s"Target out of range! Piece range is ${attacker.range}."
    }
  }

  //Execute an attack
  private def executeAttack(target: GridNode, x: Int, y: Int): Unit = {
    println(s"Attacking piece at ($x, $y)")
    boom.play()
    target.health -= selectedPiece.get.damage

    if (target.health <= 0) {
      target.imagePath = "/CommandOfTheSeas/images/water.png"
      target.isPiece = false
      target.name = "Empty tile"
      detailLabel.text = s"Piece at ($x, $y) was destroyed"
    } else {
      detailLabel.text = s"Piece at ($x, $y) was attacked, remaining health: ${target.health}"
    }
    action += 1
    updateGrid()
    checkGameOver()
    attackMode = false
  }

  //Update the grid
  private def updateGrid(): Unit = {
    if (action == 2) {
      nextTurn()
    }
    gridPane.children.clear()
    actionText.text = s"Actions left : ${2 - action}"
    pieces.foreach { piece =>
      placeCharacter(piece.xPos, piece.yPos, piece)
    }
  }

  //Move button clicked
  def onMoveButtonClicked(): Unit = {
    if (currentTeam != (if (selectedPiece.isDefined) selectedPiece.get.team else currentTeam)) {
      detailLabel.text = "It's not your turn!"
      error.play()
      return
    }
    if (moveMode) {
      moveMode = false
      detailLabel.text = "Move mode canceled."
    } else {
      if (selectedPiece.isDefined) {
        moveMode = true
        attackMode = false
        detailLabel.text = "Move mode activated. Click on a new position to move the piece."
      } else {
        error.play()
        detailLabel.text = "Select a piece first!"
      }
    }
  }

  //Attack button clicked
  def onAttackButtonClicked(): Unit = {
    if (currentTeam != (if (selectedPiece.isDefined) selectedPiece.get.team else currentTeam)) {
      detailLabel.text = "It's not your turn!"
      error.play()
      return
    }

    if (attackMode) {
      attackMode = false
      detailLabel.text = "Attack mode deactivated."
    } else {
      if (selectedPiece.isDefined) {
        attackMode = true
        moveMode = false
        detailLabel.text = "Attack mode activated. Click on an enemy piece to attack."
      } else {
        detailLabel.text = "Select a piece first!"
        error.play()
      }
    }
  }

  //Next turn
  private def nextTurn(): Unit = {
    currentTeam = if (currentTeam == 1) 2 else 1
    action = 0
    turnText.text = s"Player $currentTeam's turn"
  }

  //Check if game is over
  private def checkGameOver(): Unit = {
    val team1Pieces = pieces.count(p => p.isPiece && p.team == 1)
    val team2Pieces = pieces.count(p => p.isPiece && p.team == 2)

    if (team1Pieces == 0) {
      Main.showGameOver("Player 2 Wins!")
    } else if (team2Pieces == 0) {
      Main.showGameOver("Player 1 Wins!")
    }
  }
}