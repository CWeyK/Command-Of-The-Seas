package CommandOfTheSeas.view

import CommandOfTheSeas.Main
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

@sfxml
class GameOverController (private val winnerText :Text){
  def menu(): Unit = {
    Main.showStartMenu()
  }
  def exit(): Unit = {
    System.exit(0);
  }
  def setWinner(winner: String): Unit = {
    winnerText.text = winner
  }
}