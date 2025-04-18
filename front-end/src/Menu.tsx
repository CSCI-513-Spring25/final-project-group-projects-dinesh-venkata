import { useState } from "react";
function Menu(){
    const [widgets,setWidgets] =useState<string[]>([]);
    function handleOnDrag(e:React.DragEvent,widgetType:string){
        e.dataTransfer.setData("widgetType",widgetType);
    }
    function handleOnDrop(e:React.DragEvent){
        const widgetType = e.dataTransfer.getData("widgetType") as string;
        console.log("widgetType:", widgetType);
        setWidgets([...widgets, widgetType]);
    }
    function handleDragOver(e:React.DragEvent){
        e.preventDefault();
    }
    return (
        <div className ="Menu">
            <div className="widgets">
                <div className="widget" draggable onDragStart={(e)=>handleOnDrag(e,"Widget A")}>
                    Widget A
                </div>
                <div className="widget" draggable onDragStart={(e)=> handleOnDrag(e,"Widget B")}>Widget B</div>
                <div className="widget" draggable onDragStart={(e)=> handleOnDrag(e,"Widget C")}>Widget C</div>
            </div>
            <div className="page" onDrop = {handleOnDrop} onDragOver={handleDragOver}>
                Drop Zone
                {widgets.map((widget,index)=>(<div className="dropped-widget" key="index">{widget}</div>))}

            </div>
        </div>
    )
}
export default Menu;