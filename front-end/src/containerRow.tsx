import App from './App';
import Menu from './Menu';
import React from 'react';
 class ContainerRow extends React.Component {
 render(){
    return (
        <div className='rowC'>
             {/* <Menu />   */}
            <App />
            
        </div>
    );
    }
 }
 export default ContainerRow;