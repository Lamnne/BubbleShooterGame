import { motion } from 'motion/react';

interface LevelSelectionProps {
  difficulty: 'easy' | 'medium' | 'hard';
  onLevelSelect: (level: number) => void;
  onBack: () => void;
}

export default function LevelSelection({ difficulty, onLevelSelect, onBack }: LevelSelectionProps) {
  const maxLevels = difficulty === 'easy' ? 15 : difficulty === 'medium' ? 25 : 35;
  const unlockedLevels = 3;

  const difficultyColors = {
    easy: { bg: 'from-green-500 to-emerald-600', text: 'text-green-300', title: 'EASY' },
    medium: { bg: 'from-yellow-500 to-amber-600', text: 'text-yellow-300', title: 'MEDIUM' },
    hard: { bg: 'from-red-500 to-rose-600', text: 'text-red-300', title: 'HARD' },
  };

  const colors = difficultyColors[difficulty];

  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.8 }}
      animate={{ opacity: 1, scale: 1 }}
      exit={{ opacity: 0, scale: 0.8 }}
      transition={{ duration: 0.5 }}
      className="flex flex-col items-center justify-center gap-8 p-8 max-w-4xl"
    >
      <motion.div
        initial={{ y: -30, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.2 }}
        className="text-center"
      >
        <h1
          className="text-6xl text-white tracking-wider font-black mb-2"
          style={{
            textShadow: '0 0 30px rgba(59, 130, 246, 0.8)',
          }}
        >
          LEVEL SELECTION
        </h1>
        <p className={`text-3xl ${colors.text} font-bold`}>{colors.title}</p>
      </motion.div>

      <motion.div
        className="grid grid-cols-5 gap-4"
        initial={{ y: 30, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.3 }}
      >
        {[...Array(maxLevels)].map((_, i) => {
          const level = i + 1;
          const isUnlocked = level <= unlockedLevels;

          return (
            <motion.button
              key={level}
              onClick={() => isUnlocked && onLevelSelect(level)}
              disabled={!isUnlocked}
              className={`w-28 h-28 rounded-2xl border-4 text-3xl font-black transition-all ${
                isUnlocked
                  ? `bg-gradient-to-br ${colors.bg} border-white text-white shadow-lg hover:shadow-2xl`
                  : 'bg-gray-700 border-gray-600 text-gray-500 cursor-not-allowed'
              }`}
              initial={{ scale: 0, rotate: -180 }}
              animate={{ scale: 1, rotate: 0 }}
              transition={{ delay: 0.4 + i * 0.03, type: 'spring' }}
              whileHover={isUnlocked ? { scale: 1.1, rotate: 5 } : {}}
              whileTap={isUnlocked ? { scale: 0.95 } : {}}
            >
              {isUnlocked ? (
                <span style={{ textShadow: '0 2px 8px rgba(0,0,0,0.5)' }}>{level}</span>
              ) : (
                <motion.div
                  className="w-8 h-8 border-4 border-gray-500 rounded mx-auto"
                  animate={{ rotate: 360 }}
                  transition={{ duration: 0.5, repeat: 0 }}
                />
              )}
            </motion.button>
          );
        })}
      </motion.div>

      <motion.button
        onClick={onBack}
        className="mt-4 px-12 py-4 bg-gradient-to-r from-red-700 to-red-900 text-white text-2xl font-black tracking-wider rounded-xl border-4 border-red-500"
        initial={{ y: 30, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.5 }}
        whileHover={{ scale: 1.05 }}
        whileTap={{ scale: 0.98 }}
      >
        BACK
      </motion.button>
    </motion.div>
  );
}
