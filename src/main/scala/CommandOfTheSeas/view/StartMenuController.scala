package CommandOfTheSeas.view

import CommandOfTheSeas.Main
import scalafx.scene.media.AudioClip
import scalafxml.core.macros.sfxml

@sfxml
class StartMenuController{

  def play(): Unit = {
    Main.showGame()
  }
  def tutorial(): Unit = {
    Main.showTutorial1()
  }
  def exit(): Unit = {
    System.exit(0);
  }
}