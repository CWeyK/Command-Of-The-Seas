package CommandOfTheSeas.view

import CommandOfTheSeas.Main
import scalafxml.core.macros.sfxml

@sfxml
class Tutorial3Controller{
  def next(): Unit = {
    Main.showStartMenu()
  }
  def previous(): Unit = {
    Main.showTutorial2()
  }
}
