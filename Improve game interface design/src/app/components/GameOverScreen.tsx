import { motion } from 'motion/react';

interface GameOverScreenProps {
  level: number;
  onRestartLevel: () => void;
  onMainMenu: () => void;
}

export default function GameOverScreen({ level, onRestartLevel, onMainMenu }: GameOverScreenProps) {
  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="size-full flex flex-col items-center justify-center gap-12"
    >
      <motion.h1
        initial={{ scale: 0, rotate: -180 }}
        animate={{ scale: 1, rotate: 0 }}
        transition={{ delay: 0.2, type: 'spring', stiffness: 200 }}
        className="text-9xl font-black tracking-wider text-red-500"
        style={{
          textShadow: '0 0 40px rgba(239, 68, 68, 0.8), 0 0 80px rgba(239, 68, 68, 0.5)',
        }}
      >
        GAME OVER
      </motion.h1>

      <motion.div
        className="flex flex-col gap-5 w-96"
        initial={{ y: 50, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.5 }}
      >
        <motion.button
          onClick={onRestartLevel}
          className="px-10 py-5 bg-gradient-to-r from-yellow-500 to-yellow-700 text-white text-2xl font-black tracking-wider rounded-xl border-4 border-yellow-300 shadow-lg"
          whileHover={{ scale: 1.05, y: -4 }}
          whileTap={{ scale: 0.98 }}
        >
          Restart Level
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
        className="absolute top-32 flex gap-4"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.3 }}
      >
        {[...Array(8)].map((_, i) => {
          const colors = ['#ef4444', '#dc2626', '#b91c1c', '#991b1b'];
          return (
            <motion.div
              key={i}
              className="w-12 h-12 rounded-full"
              style={{
                backgroundColor: colors[i % colors.length],
                boxShadow: '0 0 20px rgba(239, 68, 68, 0.5)',
              }}
              animate={{
                y: [0, -100, 800],
                opacity: [1, 1, 0],
              }}
              transition={{
                duration: 2,
                repeat: Infinity,
                delay: i * 0.2,
              }}
            />
          );
        })}
      </motion.div>
    </motion.div>
  );
}
