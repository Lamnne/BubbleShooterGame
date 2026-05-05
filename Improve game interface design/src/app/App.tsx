import { useState } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import { Volume2, VolumeX } from 'lucide-react';
import LoginScreen from './components/LoginScreen';
import ModeSelection from './components/ModeSelection';
import DifficultySelection from './components/DifficultySelection';
import LevelSelection from './components/LevelSelection';
import GamePlay from './components/GamePlay';
import PauseMenu from './components/PauseMenu';
import GameOverScreen from './components/GameOverScreen';
import LevelClearScreen from './components/LevelClearScreen';
import VictoryScreen from './components/VictoryScreen';
import DuoGamePlay from './components/DuoGamePlay';
import DuoVictoryScreen from './components/DuoVictoryScreen';

type Screen = 'login' | 'mode' | 'difficulty' | 'levelSelect' | 'game' | 'duoGame' | 'gameOver' | 'levelClear' | 'victory' | 'duoVictory';
type Mode = 'single' | 'duo' | null;
type Difficulty = 'easy' | 'medium' | 'hard' | null;

export default function App() {
  const [screen, setScreen] = useState<Screen>('login');
  const [username, setUsername] = useState('');
  const [mode, setMode] = useState<Mode>(null);
  const [difficulty, setDifficulty] = useState<Difficulty>(null);
  const [selectedLevel, setSelectedLevel] = useState(1);
  const [isMuted, setIsMuted] = useState(false);
  const [isPaused, setIsPaused] = useState(false);
  const [duoWinner, setDuoWinner] = useState<1 | 2>(1);

  const handleLogin = (name: string) => {
    setUsername(name);
    setScreen('mode');
  };

  const handleModeSelect = (selectedMode: 'single' | 'duo') => {
    setMode(selectedMode);
    if (selectedMode === 'duo') {
      setScreen('duoGame');
    } else {
      setScreen('difficulty');
    }
  };

  const handleDifficultySelect = (diff: 'easy' | 'medium' | 'hard') => {
    setDifficulty(diff);
    setScreen('levelSelect');
  };

  const handleLevelSelect = (level: number) => {
    setSelectedLevel(level);
    setScreen('game');
  };

  const handleLogout = () => {
    setUsername('');
    setMode(null);
    setDifficulty(null);
    setScreen('login');
  };

  const handleBackToMode = () => {
    setDifficulty(null);
    setScreen('mode');
  };

  const handleBackToDifficulty = () => {
    setScreen('difficulty');
  };

  const handleMainMenu = () => {
    setIsPaused(false);
    setScreen('mode');
  };

  const handleRestartLevel = () => {
    setIsPaused(false);
    if (mode === 'duo') {
      setScreen('duoGame');
    } else {
      setScreen('game');
    }
  };

  const handleGameOver = () => {
    setScreen('gameOver');
  };

  const handleLevelClear = () => {
    setScreen('levelClear');
  };

  const handleVictory = () => {
    setScreen('victory');
  };

  const handleNextLevel = () => {
    const maxLevels = difficulty === 'easy' ? 15 : difficulty === 'medium' ? 25 : 35;
    if (selectedLevel < maxLevels) {
      setSelectedLevel(selectedLevel + 1);
      setScreen('game');
    } else {
      setScreen('victory');
    }
  };

  const handleDuoVictory = (winner: 1 | 2) => {
    setDuoWinner(winner);
    setScreen('duoVictory');
  };

  const handlePlayAgain = () => {
    setSelectedLevel(1);
    setScreen('difficulty');
  };

  return (
    <div className="size-full bg-gradient-to-br from-gray-900 via-blue-900 to-purple-900 flex items-center justify-center overflow-hidden relative">
      <div className="absolute inset-0 bg-[url('data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNjAiIGhlaWdodD0iNjAiIHZpZXdCb3g9IjAgMCA2MCA2MCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48ZyBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPjxnIGZpbGw9IiMwMDAiIGZpbGwtb3BhY2l0eT0iMC4xIj48cGF0aCBkPSJNMzYgMzRjMC0yLjIxLTEuNzktNC00LTRzLTQgMS43OS00IDQgMS43OSA0IDQgNCA0LTEuNzkgNC00em0wLTEwYzAtMi4yMS0xLjc5LTQtNC00cy00IDEuNzktNCA0IDEuNzkgNCA0IDQgNC0xLjc5IDQtNHoiLz48L2c+PC9nPjwvc3ZnPg==')] opacity-20"></div>

      <AnimatePresence mode="wait">
        {screen === 'login' && (
          <LoginScreen key="login" onLogin={handleLogin} />
        )}

        {screen === 'mode' && (
          <ModeSelection
            key="mode"
            onModeSelect={handleModeSelect}
            onLogout={handleLogout}
            username={username}
          />
        )}

        {screen === 'difficulty' && (
          <DifficultySelection
            key="difficulty"
            onDifficultySelect={handleDifficultySelect}
            onBack={handleBackToMode}
          />
        )}

        {screen === 'levelSelect' && difficulty && (
          <LevelSelection
            key="levelSelect"
            difficulty={difficulty}
            onLevelSelect={handleLevelSelect}
            onBack={handleBackToDifficulty}
          />
        )}

        {screen === 'game' && (
          <GamePlay
            key="game"
            level={selectedLevel}
            difficulty={difficulty || 'easy'}
            onPause={() => setIsPaused(true)}
            onGameOver={handleGameOver}
            onLevelClear={handleLevelClear}
            isMuted={isMuted}
            onToggleMute={() => setIsMuted(!isMuted)}
          />
        )}

        {screen === 'duoGame' && (
          <DuoGamePlay
            key="duoGame"
            onVictory={handleDuoVictory}
            isMuted={isMuted}
            onToggleMute={() => setIsMuted(!isMuted)}
          />
        )}

        {screen === 'gameOver' && (
          <GameOverScreen
            key="gameOver"
            level={selectedLevel}
            onRestartLevel={handleRestartLevel}
            onMainMenu={handleMainMenu}
          />
        )}

        {screen === 'levelClear' && (
          <LevelClearScreen
            key="levelClear"
            level={selectedLevel}
            onNextLevel={handleNextLevel}
            onRestartLevel={handleRestartLevel}
            onMainMenu={handleMainMenu}
          />
        )}

        {screen === 'victory' && (
          <VictoryScreen
            key="victory"
            difficulty={difficulty || 'easy'}
            onPlayAgain={handlePlayAgain}
            onMainMenu={handleMainMenu}
          />
        )}

        {screen === 'duoVictory' && (
          <DuoVictoryScreen
            key="duoVictory"
            winner={duoWinner}
            onPlayAgain={() => setScreen('duoGame')}
            onMainMenu={handleMainMenu}
          />
        )}
      </AnimatePresence>

      {isPaused && (screen === 'game' || screen === 'duoGame') && (
        <PauseMenu
          onResume={() => setIsPaused(false)}
          onRestartLevel={handleRestartLevel}
          onMainMenu={handleMainMenu}
        />
      )}

      {screen !== 'login' && (
        <motion.button
          onClick={() => setIsMuted(!isMuted)}
          className="absolute top-6 left-6 p-3 bg-black/40 backdrop-blur-sm rounded-full text-white hover:bg-black/60 transition-colors z-50"
          whileHover={{ scale: 1.1 }}
          whileTap={{ scale: 0.95 }}
        >
          {isMuted ? <VolumeX size={24} /> : <Volume2 size={24} />}
        </motion.button>
      )}
    </div>
  );
}
