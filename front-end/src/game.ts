interface GameState {
  cells: Cell[];
  widgets: string[];
  setWidgets: string[];
  name: string;
  winner?: string;
}

interface Cell {
  text: string;
  playable: boolean;
  x: number;
  y: number;
}

export type { GameState, Cell }