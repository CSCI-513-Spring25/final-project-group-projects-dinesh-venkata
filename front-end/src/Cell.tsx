import React from 'react';
import { Cell } from './game';

interface Props {
  cell: Cell;
}

class BoardCell extends React.Component<Props> {
  private static imageMap = {
    'P': require("./images/fastPirate.jpg"),
    'C': require("./images/ship.jpg"),
    'I': require("./images/island.jpg"),
    'Q': require("./images/pirateShip.jpg"),
    'M': require("./images/shark.jpg"),
    'W': require("./images/whirlpool.jpg"),
    'T': require("./images/treasure.jpg"),
    'S': require("./images/shield.jpg"),
    'H': require("./images/pirateIsland.jpg")
  };

  render(): React.ReactNode {
    const { cell } = this.props;
    const imageSrc = BoardCell.imageMap[cell.text as keyof typeof BoardCell.imageMap];

    if (imageSrc) {
      return (
        <div className="cell-image-container">
          <img 
            src={imageSrc} 
            alt="cell content" 
            className="cell-image"
            style={{
              width: '90%',
              height: '90%',
              objectFit: 'contain',
              pointerEvents: 'none'
            }}
          />
        </div>
      );
    }
    
    return (
      <div className="cell">{cell.text}</div>
    );
  }
}

export default BoardCell;