import React from 'react';
import { Cell } from './game';


interface Props {
  cell: Cell
}

class BoardCell extends React.Component<Props> {
  render(): React.ReactNode {    
    if(this.props.cell.text=='P')
    return (
      <img src={require(".//images//fastPirate.jpg")} alt="Nothing" className={`resizecell `}></img>
    )
    else if(this.props.cell.text=='C')
      return (   
      <img src={require(".//images//ship.jpg")} alt="Nothing" className={`resizecell `}></img>  
      )
      else if(this.props.cell.text=='I')
        return (
          <img src={require(".//images//island.jpg")} alt="Nothing" className={`resizecell `}></img>  
        )
      else if(this.props.cell.text=='Q')
        return (
          <img src={require(".//images//pirateShip.jpg")} alt="Nothing" className={`resizecell `}></img>  
        )
        else if(this.props.cell.text=='M')
          return (
            <img src={require(".//images//shark.jpg")} alt="Nothing" className={`resizecell `}></img>  
          )
    else if(this.props.cell.text=='W')
      return (
        <img src={require(".//images//whirlpool.jpg")} alt="Nothing" className={`resizecell `}></img> 
      )
      else if(this.props.cell.text=='T')
        return (
        <img src={require(".//images//treasure.jpg")} alt="Nothing" className={`resizecell `}></img> 
        )
        else if (this.props.cell.text=='S')
          return (
            <img src={require(".//images//shield.jpg")} alt="Nothing" className={`resizecell `}></img> 
            )
      else
      return (
        <div className={`cell ` }>{this.props.cell.text}</div>
      )
  }
}

export default BoardCell;