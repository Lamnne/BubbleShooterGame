import { motion } from 'motion/react';
import { Trophy } from 'lucide-react';

interface DuoVictoryScreenProps {
  winner: 1 | 2;
  onPlayAgain: () => void;
  onMainMenu: () => void;
}

export default function DuoVictoryScreen({ winner, onPlayAgain, onMainMenu }: DuoVictoryScreenProps) {
  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="size-full flex flex-col items-center justify-center gap-12"
    >
      <motion.div
        className="text-center"
        initial={{ scale: 0, rotate: -180 }}
        animate={{ scale: 1, rotate: 0 }}
        transition={{ delay: 0.2, type: 'spring', stiffness: 150 }}
      >
        <motion.div
          className="flex items-center justify-center gap-6 mb-6"
          animate={{
            y: [0, -20, 0],
          }}
          transition={{
            duration: 2,
            repeat: Infinity,
          }}
        >
          <Trophy className="text-yellow-400 fill-yellow-400" size={80} />
        </motion.div>

        <h1
          className="text-8xl font-black tracking-wider text-yellow-400 mb-4"
          style={{
            textShadow: '0 0 40px rgba(250, 204, 21, 0.8), 0 0 80px rgba(250, 204, 21, 0.5)',
          }}
        >
          PLAYER {winner} WINS!
        </h1>

        <motion.p
          className="text-3xl text-white font-bold"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.5 }}
        >
          Congratulations!
        </motion.p>
      </motion.div>

      <motion.div
        className="flex flex-col gap-5 w-96"
        initial={{ y: 50, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.7 }}
      >
        <motion.button
          onClick={onPlayAgain}
          className="px-10 py-5 bg-gradient-to-r from-green-500 to-green-700 text-white text-2xl font-black tracking-wider rounded-xl border-4 border-green-300 shadow-lg"
          whileHover={{ scale: 1.05, y: -4 }}
          whileTap={{ scale: 0.98 }}
        >
          Play Again
        </motion.button>

        <motion.button
          onClick={onMainMenu}
          className="px-10 py-5 bg-gradient-to-r from-red-600 to-red-800 text-white text-2xl font-black tracking-wider rounded-xl border-4 border-red-400 shadow-lg"
          whileHover={{ scale: 1.05, y: -4 }}
          whileTap={{ scale: 0.98 }}
        >
          Main Menu
        </motion.button>
      </motion.div>

      <motion.div
        className="absolute inset-0 pointer-events-none overflow-hidden"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.3 }}
      >
        {[...Array(50)].map((_, i) => {
          const colors = ['#ef4444', '#3b82f6', '#22c55e', '#eab308', '#8b5cf6'];
          return (
            <motion.div
              key={i}
              className="absolute w-3 h-3 rounded-full"
              style={{
                left: `${Math.random() * 100}%`,
                top: -20,
                backgroundColor: colors[Math.floor(Math.random() * colors.length)],
              }}
              animate={{
                y: [0, window.innerHeight + 50],
                x: [0, (Math.random() - 0.5) * 200],
                rotate: [0, 360],
              }}
              transition={{
                duration: 2 + Math.random() * 2,
                repeat: Infinity,
                delay: Math.random() * 2,
              }}
            />
          );
        })}
      </motion.div>
    </motion.div>
  );
}
