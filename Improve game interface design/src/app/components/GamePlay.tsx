import { useState, useEffect } from 'react';
import { motion } from 'motion/react';
import { Pause, Volume2, VolumeX } from 'lucide-react';

interface GamePlayProps {
  level: number;
  difficulty: string;
  onPause: () => void;
  onGameOver: () => void;
  onLevelClear: () => void;
  isMuted: boolean;
  onToggleMute: () => void;
}

interface Bubble {
  id: number;
  color: string;
  x: number;
  y: number;
}

export default function GamePlay({ level, difficulty, onPause, onGameOver, onLevelClear, isMuted, onToggleMute }: GamePlayProps) {
  const [balls, setBalls] = useState(59);
  const colors = ['#ef4444', '#3b82f6', '#22c55e', '#eab308'];
  const [bubbles, setBubbles] = useState<Bubble[]>([]);
  const [nextBubbleColor, setNextBubbleColor] = useState(colors[0]);
  const [currentBubbleColor, setCurrentBubbleColor] = useState(colors[1]);

  useEffect(() => {
    const initialBubbles: Bubble[] = [];
    const rows = 4;
    const bubblesPerRow = 18;

    for (let row = 0; row < rows; row++) {
      for (let col = 0; col < bubblesPerRow; col++) {
        const offset = row % 2 === 0 ? 0 : 20;
        initialBubbles.push({
          id: row * bubblesPerRow + col,
          color: colors[Math.floor(Math.random() * colors.length)],
          x: col * 40 + offset,
          y: row * 40,
        });
      }
    }
    setBubbles(initialBubbles);
  }, [level]);

  const maxLevels = difficulty === 'easy' ? 15 : difficulty === 'medium' ? 25 : 35;

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="w-full h-full flex flex-col"
    >
      <div className="flex items-center justify-between px-8 py-4 bg-black/40 backdrop-blur-sm">
        <div className="flex items-center gap-6">
          <motion.button
            onClick={onToggleMute}
            className="p-3 bg-white/10 rounded-full hover:bg-white/20 transition-colors"
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.95 }}
          >
            {isMuted ? <VolumeX className="text-white" size={24} /> : <Volume2 className="text-white" size={24} />}
          </motion.button>
          <span className="text-white text-xl font-bold">LEVEL {level} / {maxLevels}</span>
        </div>

        <span className="text-white text-xl font-bold">BALLS: {balls}</span>

        <motion.button
          onClick={onPause}
          className="p-3 bg-white/10 rounded-full hover:bg-white/20 transition-colors"
          whileHover={{ scale: 1.1 }}
          whileTap={{ scale: 0.95 }}
        >
          <Pause className="text-white" size={24} />
        </motion.button>
      </div>

      <div className="flex-1 flex">
        <GameBoard
          bubbles={bubbles}
          currentColor={currentBubbleColor}
          nextColor={nextBubbleColor}
          onGameOver={onGameOver}
          onLevelClear={onLevelClear}
        />
      </div>
    </motion.div>
  );
}

interface GameBoardProps {
  bubbles: Bubble[];
  currentColor: string;
  nextColor: string;
  onGameOver: () => void;
  onLevelClear: () => void;
}

function GameBoard({ bubbles, currentColor, nextColor, onGameOver, onLevelClear }: GameBoardProps) {
  return (
    <div className="flex-1 relative">
      <div className="absolute inset-0 flex flex-col">
        <div className="flex-1 relative overflow-hidden">
          <motion.div
            className="absolute top-0 left-1/2 -translate-x-1/2 w-full max-w-3xl"
            initial={{ y: -100 }}
            animate={{ y: 0 }}
            transition={{ duration: 0.8, type: 'spring' }}
          >
            {bubbles.map((bubble, index) => (
              <motion.div
                key={bubble.id}
                className="absolute rounded-full shadow-lg cursor-pointer hover:scale-110 transition-transform"
                style={{
                  width: 36,
                  height: 36,
                  left: bubble.x,
                  top: bubble.y,
                  backgroundColor: bubble.color,
                  boxShadow: `0 0 15px ${bubble.color}80, inset 0 -8px 12px rgba(0,0,0,0.3), inset 0 8px 12px rgba(255,255,255,0.3)`,
                }}
                initial={{ scale: 0, y: -200 }}
                animate={{ scale: 1, y: 0 }}
                transition={{ delay: index * 0.01, type: 'spring', stiffness: 200 }}
              />
            ))}
          </motion.div>

          <div className="absolute bottom-4 left-0 right-0 text-red-500 text-xl font-bold text-center">
            DANGER
          </div>
          <div className="absolute bottom-8 left-0 right-0 border-t-4 border-dashed border-red-500/50"></div>
        </div>

        <div className="h-32 bg-gradient-to-t from-black/60 to-transparent flex items-center justify-center gap-8">
          <div className="flex items-center gap-6">
            <div className="flex flex-col items-center gap-2">
              <motion.div
                className="w-16 h-16 rounded-full shadow-2xl"
                style={{
                  backgroundColor: currentColor,
                  boxShadow: `0 0 30px ${currentColor}, inset 0 -10px 15px rgba(0,0,0,0.4), inset 0 10px 15px rgba(255,255,255,0.4)`,
                }}
                animate={{
                  y: [0, -10, 0],
                }}
                transition={{
                  duration: 1,
                  repeat: Infinity,
                }}
              />
              <span className="text-white text-sm font-bold">CURRENT</span>
            </div>

            <div className="text-white text-2xl font-bold">NEXT</div>

            <motion.div
              className="w-14 h-14 rounded-full shadow-xl"
              style={{
                backgroundColor: nextColor,
                boxShadow: `0 0 20px ${nextColor}80, inset 0 -8px 12px rgba(0,0,0,0.3), inset 0 8px 12px rgba(255,255,255,0.3)`,
              }}
              animate={{
                rotate: 360,
              }}
              transition={{
                duration: 3,
                repeat: Infinity,
                ease: 'linear',
              }}
            />
          </div>
        </div>
      </div>

      <div className="absolute bottom-36 right-8 bg-black/60 backdrop-blur-sm p-4 rounded-xl text-white space-y-2">
        <div className="flex items-center gap-3">
          <div className="w-6 h-6 rounded-full bg-red-500 border-2 border-white"></div>
          <span className="text-sm">= Bomb (area)</span>
        </div>
        <div className="flex items-center gap-3">
          <div className="w-6 h-6 rounded-full bg-orange-500 border-2 border-white flex items-center justify-center text-xs font-bold">5</div>
          <span className="text-sm">= Countdown</span>
        </div>
        <div className="flex items-center gap-3">
          <div className="w-6 h-6 rounded bg-purple-500 border-2 border-white"></div>
          <span className="text-sm">= Clear color</span>
        </div>
      </div>
    </div>
  );
}
