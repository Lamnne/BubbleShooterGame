import { motion } from 'motion/react';

interface ModeSelectionProps {
  onModeSelect: (mode: 'single' | 'duo') => void;
  onLogout: () => void;
  username: string;
}

export default function ModeSelection({ onModeSelect, onLogout, username }: ModeSelectionProps) {
  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.8 }}
      animate={{ opacity: 1, scale: 1 }}
      exit={{ opacity: 0, scale: 0.8 }}
      transition={{ duration: 0.5 }}
      className="flex flex-col items-center justify-center gap-12 p-8"
    >
      <motion.div
        initial={{ y: -30, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.2 }}
        className="text-center"
      >
        <h1
          className="text-7xl text-white tracking-wider font-black mb-4"
          style={{
            textShadow: '0 0 30px rgba(59, 130, 246, 0.8)',
          }}
        >
          CHOOSE MODE
        </h1>
        <p className="text-cyan-300 text-xl">Welcome, {username}!</p>
      </motion.div>

      <motion.div
        className="flex flex-col gap-6 w-[600px]"
        initial={{ y: 30, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.3 }}
      >
        <motion.button
          onClick={() => onModeSelect('single')}
          className="px-12 py-6 bg-gradient-to-r from-blue-500 to-blue-700 text-white text-3xl font-black tracking-wider rounded-2xl border-4 border-blue-300 relative overflow-hidden group"
          whileHover={{ scale: 1.05, y: -4 }}
          whileTap={{ scale: 0.98 }}
        >
          <motion.div
            className="absolute inset-0 bg-gradient-to-r from-blue-400 to-blue-600"
            initial={{ x: '-100%' }}
            whileHover={{ x: 0 }}
            transition={{ duration: 0.3 }}
          />
          <span className="relative z-10" style={{ textShadow: '0 2px 10px rgba(0,0,0,0.5)' }}>
            SINGLE PLAYER
          </span>
        </motion.button>

        <motion.button
          onClick={() => onModeSelect('duo')}
          className="px-12 py-6 bg-gradient-to-r from-orange-500 to-red-600 text-white text-3xl font-black tracking-wider rounded-2xl border-4 border-orange-300 relative overflow-hidden group"
          whileHover={{ scale: 1.05, y: -4 }}
          whileTap={{ scale: 0.98 }}
        >
          <motion.div
            className="absolute inset-0 bg-gradient-to-r from-orange-400 to-red-500"
            initial={{ x: '-100%' }}
            whileHover={{ x: 0 }}
            transition={{ duration: 0.3 }}
          />
          <span className="relative z-10" style={{ textShadow: '0 2px 10px rgba(0,0,0,0.5)' }}>
            DUO VERSUS
          </span>
        </motion.button>

        <motion.button
          onClick={onLogout}
          className="px-12 py-6 bg-gradient-to-r from-red-700 to-red-900 text-white text-3xl font-black tracking-wider rounded-2xl border-4 border-red-500 relative overflow-hidden"
          whileHover={{ scale: 1.05, y: -4 }}
          whileTap={{ scale: 0.98 }}
        >
          <span style={{ textShadow: '0 2px 10px rgba(0,0,0,0.5)' }}>LOGOUT</span>
        </motion.button>
      </motion.div>

      <motion.div
        className="absolute bottom-8 flex gap-3"
        animate={{
          y: [0, -10, 0],
        }}
        transition={{
          duration: 1.5,
          repeat: Infinity,
        }}
      >
        {['🔴', '🔵', '🟢', '🟡'].map((emoji, i) => (
          <motion.span
            key={i}
            className="text-5xl"
            animate={{
              rotate: [0, 360],
            }}
            transition={{
              duration: 3,
              repeat: Infinity,
              delay: i * 0.3,
            }}
          >
            {emoji}
          </motion.span>
        ))}
      </motion.div>
    </motion.div>
  );
}
