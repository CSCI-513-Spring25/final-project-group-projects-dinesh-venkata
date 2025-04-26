import React from 'react';
import './App.css';
import { GameState, Cell } from './game';
import BoardCell from './Cell';

interface Props { }

class App extends React.Component<Props, GameState> {
  private initialized: boolean = false;
  currentPlayer = 'PLAYER0';
  winner = '';

  constructor(props: Props) {
    super(props);
    this.state = { cells: [], widgets: [], setWidgets: [], name: "" };
  }

  newGame = async () => {
    const response = await fetch('/newgame');
    const json = await response.json();
    this.setState({ cells: json['cells'] });
  }

  play(x: number, y: number): React.MouseEventHandler {
    return async (e) => {
      e.preventDefault();
      const response = await fetch(`/play?x=${x}&y=${y}`);
      const json = await response.json();
      this.currentPlayer = json['currentPlayer'];
      this.setState({ cells: json['cells'] });
    }
  }

  undo = async () => {
    const response = await fetch(`/undo`);
    const json = await response.json();
    this.currentPlayer = json['currentPlayer'];
    this.setState({ cells: json['cells'] });
  }

  handleKeyPressed = async (event) => {
    const response = await fetch(`/play?keyEvent=${event.keyCode}`);
    const json = await response.json();
    this.setState({ cells: json['cells'] });
  }

  handleOnDrag = (e: React.DragEvent, widgetType: string) => {
    e.dataTransfer.setData("type", widgetType);
  }

  handleOnDrop = async (e: React.DragEvent, image: number) => {
    const type = e.dataTransfer.getData("type") as string;
    const response = await fetch(`/createObject?index=${image}&type=${type}`);
    const json = await response.json();
    this.setState({ cells: json['cells'] });
  }

  handleDragOver(e: React.DragEvent) {
    e.preventDefault();
  }

  createCell(cell: Cell, index: number): React.ReactNode {
    return (
      <div
        key={index}
        onDrop={(e) => this.handleOnDrop(e, index)}
        onDragOver={this.handleDragOver}
        className="cell-container"
      >
        <BoardCell cell={cell}></BoardCell>
      </div>
    );
  }

  componentDidMount(): void {
    if (!this.initialized) {
      this.newGame();
      this.initialized = true;
    }
    window.addEventListener("keydown", this.handleKeyPressed, false);
  }

  componentWillUnmount(): void {
    window.removeEventListener("keydown", this.handleKeyPressed, false);
  }

  render(): React.ReactNode {
    return (
      <div className="app-container">
        <div className="game-banner">
          <h1>Christopher Columbus - Game</h1>
        </div>

        <div className="content-container">
          <div className="images-panel">
            <div className="image-item" draggable onDragStart={(e) => this.handleOnDrag(e, "P")}>
              Fast Pirate
              <img src={require("./images/fastPirate.jpg")} alt="Fast Pirate" className="image" />
            </div>
            <div className="image-item" draggable onDragStart={(e) => this.handleOnDrag(e, "C")}>
              Columbus Ship
              <img src={require("./images/ship.jpg")} alt="Columbus Ship" className="image" />
            </div>
            <div className="image-item" draggable onDragStart={(e) => this.handleOnDrag(e, "I")}>
              Island
              <img src={require("./images/island.jpg")} alt="Island" className="image" />
            </div>
            <div className="image-item" draggable onDragStart={(e) => this.handleOnDrag(e, "Q")}>
              Slow Pirate
              <img src={require("./images/pirateShip.jpg")} alt="Slow Pirate" className="image" />
            </div>
            <div className="image-item" draggable onDragStart={(e) => this.handleOnDrag(e, "M")}>
              Shark
              <img src={require("./images/shark.jpg")} alt="Shark" className="image" />
            </div>
            <div className="image-item" draggable onDragStart={(e) => this.handleOnDrag(e, "W")}>
              WhirlPool
              <img src={require("./images/whirlpool.jpg")} alt="WhirlPool" className="image" />
            </div>
            <div className="image-item" draggable onDragStart={(e) => this.handleOnDrag(e, "T")}>
              Treasure
              <img src={require("./images/treasure.jpg")} alt="Treasure" className="image" />
            </div>
            <div className="image-item" draggable onDragStart={(e) => this.handleOnDrag(e, "S")}>
              Shield
              <img src={require("./images/shield.jpg")} alt="Shield" className="image" />
            </div>
          </div>

          <div className="game-area">
            <div id="board">
              {this.state.cells.map((cell, i) => this.createCell(cell, i))}
            </div>
          </div>
        </div>

        <div id="bottombar">
          <button onClick={this.newGame}>New Game</button>
        </div>
      </div>
    );
  }
}

export default App;