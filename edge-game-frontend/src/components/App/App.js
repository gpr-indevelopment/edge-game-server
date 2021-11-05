import React from 'react';
import styles from "./App.module.css";
import VideoPlayer from "../VideoPlayer/VideoPlayer";

function App() {
  return (
    <div className={styles.container}>
      <VideoPlayer/>
    </div>
  );
}

export default App;
