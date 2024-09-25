package CommandOfTheSeas.model

import javafx.fxml.FXML
import scalafx.scene.image.{Image, ImageView}
import scalafx.collections.ObservableBuffer

class GridNode(
                var health: Int,
                var range: Int,
                var damage: Int,
                var speed: Int,
                var isPiece: Boolean,
                var xPos: Int,
                var yPos: Int,
                val team: Int,
                var imagePath: String,
                var name: String) {

  def this(isPiece: Boolean, xPos: Int, yPos: Int, team: Int) = {
    this(
      if (isPiece) 100 else 0, // Health
      if (isPiece) 2 else 0,  // Range
      if (isPiece) 50 else 0,  // Damage
      if (isPiece) 2 else 0,   // Speed
      isPiece,
      xPos,
      yPos,
      team,
      imagePath = "/CommandOfTheSeas/images/water.png",
      name = "Empty tile"
    )
  }
  def displayStats(): Unit = {
    println(s"Health: $health, Damage: $damage, Speed: $speed, Position: ($xPos, $yPos)")
  }

  def updatePosition(x: Int, y: Int): Unit = {
    xPos = x
    yPos = y
  }
}