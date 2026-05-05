import { motion } from 'motion/react';

interface PauseMenuProps {
  onResume: () => void;
  onRestartLevel: () => void;
  onMainMenu: () => void;
}

export default function PauseMenu({ onResume, onRestartLevel, onMainMenu }: PauseMenuProps) {
  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="absolute inset-0 bg-black/80 backdrop-blur-md flex items-center justify-center z-50"
      onClick={onResume}
    >
      <motion.div
        initial={{ scale: 0.8, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        transition={{ delay: 0.1, type: 'spring' }}
        className="bg-gradient-to-br from-gray-800 to-gray-900 p-12 rounded-3xl shadow-2xl border-4 border-white/20"
        onClick={(e) => e.stopPropagation()}
      >
        <motion.h1
          initial={{ y: -20, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          transition={{ delay: 0.2 }}
          className="text-7xl text-white tracking-wider font-black text-center mb-12"
          style={{
            textShadow: '0 0 30px rgba(59, 130, 246, 0.8)',
          }}
        >
          PAUSED
        </motion.h1>

        <div className="flex flex-col gap-5 w-96">
          <motion.button
            onClick={onResume}
            className="px-10 py-5 bg-gradient-to-r from-green-500 to-green-700 text-white text-2xl font-black tracking-wider rounded-xl border-4 border-green-300 shadow-lg"
            initial={{ x: -50, opacity: 0 }}
            animate={{ x: 0, opacity: 1 }}
            transition={{ delay: 0.3 }}
            whileHover={{ scale: 1.05, x: 5 }}
            whileTap={{ scale: 0.98 }}
          >
            Resume
          </motion.button>

          <motion.button
            onClick={onRestartLevel}
            className="px-10 py-5 bg-gradient-to-r from-yellow-500 to-yellow-700 text-white text-2xl font-black tracking-wider rounded-xl border-4 border-yellow-300 shadow-lg"
            initial={{ x: -50, opacity: 0 }}
            animate={{ x: 0, opacity: 1 }}
            transition={{ delay: 0.4 }}
            whileHover={{ scale: 1.05, x: 5 }}
            whileTap={{ scale: 0.98 }}
          >
            Restart Level
          </motion.button>

          <motion.button
            onClick={onMainMenu}
            className="px-10 py-5 bg-gradient-to-r from-red-600 to-red-800 text-white text-2xl font-black tracking-wider rounded-xl border-4 border-red-400 shadow-lg"
            initial={{ x: -50, opacity: 0 }}
            animate={{ x: 0, opacity: 1 }}
            transition={{ delay: 0.5 }}
            whileHover={{ scale: 1.05, x: 5 }}
            whileTap={{ scale: 0.98 }}
          >
            Main Menu
          </motion.button>
        </div>
      </motion.div>
    </motion.div>
  );
}
