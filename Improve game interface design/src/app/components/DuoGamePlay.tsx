import { useState, useEffect } from 'react';
import { motion } from 'motion/react';
import { Volume2, VolumeX } from 'lucide-react';

interface DuoGamePlayProps {
  onVictory: (winner: 1 | 2) => void;
  isMuted: boolean;
  onToggleMute: () => void;
}

interface Bubble {
  id: number;
  color: string;
  x: number;
  y: number;
}

export default function DuoGamePlay({ onVictory, isMuted, onToggleMute }: DuoGamePlayProps) {
  const colors = ['#ef4444', '#3b82f6', '#22c55e', '#eab308'];
  const [bubblesLeft, setBubblesLeft] = useState<Bubble[]>([]);
  const [bubblesRight, setBubblesRight] = useState<Bubble[]>([]);

  useEffect(() => {
    const createBubbles = () => {
      const bubbles: Bubble[] = [];
      const rows = 4;
      const bubblesPerRow = 9;

      for (let row = 0; row < rows; row++) {
        for (let col = 0; col < bubblesPerRow; col++) {
          const offset = row % 2 === 0 ? 0 : 20;
          bubbles.push({
            id: row * bubblesPerRow + col,
            color: colors[Math.floor(Math.random() * colors.length)],
            x: col * 40 + offset,
            y: row * 40,
          });
        }
      }
      return bubbles;
    };

    setBubblesLeft(createBubbles());
    setBubblesRight(createBubbles());
  }, []);

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="w-full h-full flex flex-col"
    >
      <div className="flex items-center justify-center px-8 py-4 bg-black/40 backdrop-blur-sm">
        <motion.button
          onClick={onToggleMute}
          className="p-3 bg-white/10 rounded-full hover:bg-white/20 transition-colors"
          whileHover={{ scale: 1.1 }}
          whileTap={{ scale: 0.95 }}
        >
          {isMuted ? <VolumeX className="text-white" size={24} /> : <Volume2 className="text-white" size={24} />}
        </motion.button>
      </div>

      <div className="flex-1 flex divide-x-4 divide-white/20">
        <PlayerBoard
          bubbles={bubblesLeft}
          playerNum={1}
          currentColor={colors[0]}
          nextColor={colors[1]}
        />
        <PlayerBoard
          bubbles={bubblesRight}
          playerNum={2}
          currentColor={colors[2]}
          nextColor={colors[3]}
        />
      </div>
    </motion.div>
  );
}

interface PlayerBoardProps {
  bubbles: Bubble[];
  playerNum: 1 | 2;
  currentColor: string;
  nextColor: string;
}

function PlayerBoard({ bubbles, playerNum, currentColor, nextColor }: PlayerBoardProps) {
  return (
    <div className="flex-1 relative">
      <div className="absolute top-4 left-1/2 -translate-x-1/2 text-white text-2xl font-black">
        PLAYER {playerNum}
      </div>

      <div className="absolute inset-0 flex flex-col pt-16">
        <div className="flex-1 relative overflow-hidden">
          <motion.div
            className="absolute top-0 left-1/2 -translate-x-1/2 w-full max-w-md"
            initial={{ y: -100 }}
            animate={{ y: 0 }}
            transition={{ duration: 0.8, type: 'spring' }}
          >
            {bubbles.map((bubble, index) => (
              <motion.div
                key={bubble.id}
                className="absolute rounded-full shadow-lg"
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

          <div className="absolute bottom-4 left-0 right-0 text-red-500 text-lg font-bold text-center">
            DANGER
          </div>
          <div className="absolute bottom-8 left-0 right-0 border-t-4 border-dashed border-red-500/50"></div>
        </div>

        <div className="h-32 bg-gradient-to-t from-black/60 to-transparent flex items-center justify-center gap-4">
          <div className="flex items-center gap-4">
            <motion.div
              className="w-14 h-14 rounded-full shadow-2xl"
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

            <span className="text-white text-xl font-bold">NEXT</span>

            <motion.div
              className="w-12 h-12 rounded-full shadow-xl"
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
    </div>
  );
}
