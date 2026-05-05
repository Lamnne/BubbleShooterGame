import { motion } from 'motion/react';

interface DifficultySelectionProps {
  onDifficultySelect: (difficulty: 'easy' | 'medium' | 'hard') => void;
  onBack: () => void;
}

export default function DifficultySelection({ onDifficultySelect, onBack }: DifficultySelectionProps) {
  const difficulties = [
    { id: 'easy', label: 'EASY (15 Levels)', color: 'from-green-500 to-green-700', border: 'border-green-300' },
    { id: 'medium', label: 'MEDIUM (25 Levels)', color: 'from-yellow-500 to-yellow-700', border: 'border-yellow-300' },
    { id: 'hard', label: 'HARD (35 Levels)', color: 'from-red-500 to-red-700', border: 'border-red-300' },
  ];

  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.8 }}
      animate={{ opacity: 1, scale: 1 }}
      exit={{ opacity: 0, scale: 0.8 }}
      transition={{ duration: 0.5 }}
      className="flex flex-col items-center justify-center gap-12 p-8"
    >
      <motion.h1
        initial={{ y: -30, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.2 }}
        className="text-7xl text-white tracking-wider font-black"
        style={{
          textShadow: '0 0 30px rgba(59, 130, 246, 0.8)',
        }}
      >
        PUZZLE BOBBLE
      </motion.h1>

      <motion.div
        className="flex flex-col gap-6 w-[500px]"
        initial={{ y: 30, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.3 }}
      >
        {difficulties.map((diff, index) => (
          <motion.button
            key={diff.id}
            onClick={() => onDifficultySelect(diff.id as 'easy' | 'medium' | 'hard')}
            className={`px-10 py-5 bg-gradient-to-r ${diff.color} text-white text-2xl font-black tracking-wider rounded-xl border-4 ${diff.border} relative overflow-hidden`}
            initial={{ x: -100, opacity: 0 }}
            animate={{ x: 0, opacity: 1 }}
            transition={{ delay: 0.4 + index * 0.1 }}
            whileHover={{ scale: 1.05, x: 10 }}
            whileTap={{ scale: 0.98 }}
          >
            <motion.div
              className="absolute inset-0 bg-white/20"
              initial={{ x: '-100%' }}
              whileHover={{ x: '100%' }}
              transition={{ duration: 0.5 }}
            />
            <span className="relative z-10" style={{ textShadow: '0 2px 10px rgba(0,0,0,0.5)' }}>
              {diff.label}
            </span>
          </motion.button>
        ))}

        <motion.button
          onClick={onBack}
          className="px-10 py-5 bg-gradient-to-r from-red-700 to-red-900 text-white text-2xl font-black tracking-wider rounded-xl border-4 border-red-500"
          initial={{ x: -100, opacity: 0 }}
          animate={{ x: 0, opacity: 1 }}
          transition={{ delay: 0.7 }}
          whileHover={{ scale: 1.05, x: 10 }}
          whileTap={{ scale: 0.98 }}
        >
          BACK
        </motion.button>
      </motion.div>
    </motion.div>
  );
}
