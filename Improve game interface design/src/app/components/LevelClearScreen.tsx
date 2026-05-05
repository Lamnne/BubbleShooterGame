import { motion } from 'motion/react';
import { Star } from 'lucide-react';

interface LevelClearScreenProps {
  level: number;
  onNextLevel: () => void;
  onRestartLevel: () => void;
  onMainMenu: () => void;
}

export default function LevelClearScreen({ level, onNextLevel, onRestartLevel, onMainMenu }: LevelClearScreenProps) {
  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="size-full flex flex-col items-center justify-center gap-12"
    >
      <motion.div
        className="text-center"
        initial={{ scale: 0 }}
        animate={{ scale: 1 }}
        transition={{ delay: 0.2, type: 'spring', stiffness: 150 }}
      >
        <motion.div
          className="flex items-center justify-center gap-4 mb-6"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.4 }}
        >
          <Star className="text-yellow-400 fill-yellow-400" size={48} />
          <h1
            className="text-8xl font-black tracking-wider text-yellow-400"
            style={{
              textShadow: '0 0 40px rgba(250, 204, 21, 0.8), 0 0 80px rgba(250, 204, 21, 0.5)',
            }}
          >
            Level {level} Clear!
          </h1>
          <Star className="text-yellow-400 fill-yellow-400" size={48} />
        </motion.div>
      </motion.div>

      <motion.div
        className="flex flex-col gap-5 w-96"
        initial={{ y: 50, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.6 }}
      >
        <motion.button
          onClick={onNextLevel}
          className="px-10 py-5 bg-gradient-to-r from-green-500 to-green-700 text-white text-2xl font-black tracking-wider rounded-xl border-4 border-green-300 shadow-lg"
          whileHover={{ scale: 1.05, y: -4 }}
          whileTap={{ scale: 0.98 }}
        >
          Next Level
        </motion.button>

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
        className="absolute inset-0 pointer-events-none"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.3 }}
      >
        {[...Array(20)].map((_, i) => (
          <motion.div
            key={i}
            className="absolute"
            style={{
              left: `${Math.random() * 100}%`,
              top: `${Math.random() * 100}%`,
            }}
            animate={{
              scale: [0, 1, 0],
              opacity: [0, 1, 0],
            }}
            transition={{
              duration: 2,
              repeat: Infinity,
              delay: Math.random() * 2,
            }}
          >
            <Star className="text-yellow-400 fill-yellow-400" size={24} />
          </motion.div>
        ))}
      </motion.div>
    </motion.div>
  );
}
